package com.yl.quartz.demo.infra.sequence;

/**
 * @author li.yang01@hand-china.com 2021/1/15 11:44 上午
 */
@SuppressWarnings("all")
public class SnowflakeIdWorker {

    /**
     * 此处根据每台机器在集群中的id,创建SnowflakeIdWorker 单例
     */
    private static class SnowflakeIdWorkerSingleton {

        private final static SnowflakeIdWorker EPO_SNOWFLAKE_ID_WORKER = new SnowflakeIdWorker(1, 1L);
        private final static SnowflakeIdWorker EPO_SNOWFLAKE_DBID_WORKER = new SnowflakeIdWorker(2, 2L);
    }

    private static class TxIdThreadLoad {

        private final static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    }

    /**
     * 获取单例
     *
     * @return SnowflakeIdWorker
     */
    private static SnowflakeIdWorker getTxInstance() {

        return SnowflakeIdWorkerSingleton.EPO_SNOWFLAKE_ID_WORKER;
    }

    /**
     * 获取单例
     *
     * @return SnowflakeIdWorker
     */
    private static SnowflakeIdWorker getDBInstance() {

        return SnowflakeIdWorkerSingleton.EPO_SNOWFLAKE_DBID_WORKER;
    }

    /**
     * 生成id 并存放到ThreadLocal中
     *
     * @return txId
     */
    public static long getTxIdWithTL() {
        long txId = getTxInstance().nextId();
        TxIdThreadLoad.threadLocal.set(txId);
        return txId;
    }

    /**
     * 获取临时的id
     *
     * @return
     */
    public static long getTempTxId() {
        return getTxInstance().nextId();
    }

    /**
     * 获取当前线程中存储的txId
     *
     * @return
     */
    public static long getCurrentThreadTxId() {

        Long trxId = TxIdThreadLoad.threadLocal.get();
        if (null == trxId) {
            trxId = getTxIdWithTL();
        }
        return trxId;
    }

    /**
     * 获取DBInstance下一个唯一id
     *
     * @return
     */
    public static long getDbNextId() {
        return getDBInstance().nextId();
    }

    /**
     * 开始时间截 (2015-01-01)
     */
    private final long begTimePoint = 1420041600000L;

    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数
     */
    private final long dataCenterIdBits = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    /**
     * 序列在id中占的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器ID向左移12位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private final long dataCenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private long datacenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    private SnowflakeIdWorker(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("data center Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.datacenterId = dataCenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - begTimePoint) << timestampLeftShift)
                | (datacenterId << dataCenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
}
