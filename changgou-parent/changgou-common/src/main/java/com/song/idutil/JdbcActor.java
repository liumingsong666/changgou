package com.song.idutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author: mingsong.liu
 * @date: 2020-05-11 11:10
 * @description: 对jdbc的原始操作，开启事务和提交，用于学习
 */

public class JdbcActor {
    private static final Logger log = LoggerFactory.getLogger(JdbcActor.class);
    private DataSource dataSource;
    public static final String GET_FREE_WORKER_ID_SQL = "select worker_id from global_worker_id where state=1 order by worker_id limit 1 for update";
    public static final String UPDATE_WORKER_ID_STATE_WHEN_START_SQL = "update global_worker_id set state=?,p_id=?,ip=?,last_start_time=?,memo=? where worker_id=? and state=?";
    public static final String UPDATE_WORKER_ID_STATE_WHEN_SHUTDOWN_SQL = "update global_worker_id set state=?,last_shutdown_time=? where worker_id=? and state=? and ip=? and p_id=?";

    public JdbcActor(DataSource dataSource) {
        this.validate(dataSource);
        this.dataSource = dataSource;
    }

    public void init() throws SQLException {
        this.validate(this.dataSource);
        log.info("init connections:{}", this.dataSource);
        this.initConn();
        log.info("init connections success");
    }

    public int findAndUpdateWorkerIdState(String hostNameOrIP, String pid, String memo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int workerId = -1;

        try {
            connection = this.dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("select worker_id from global_worker_id where state=1 order by worker_id limit 1 for update");
            log.info("get free workerId sql:{}", "select worker_id from global_worker_id where state=1 order by worker_id limit 1 for update");
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new IdGeneratorException("can not find a valid workerId,hostNameOrIP:" + hostNameOrIP + ",pid:" + pid + ",memo:" + memo);
            }

            workerId = resultSet.getInt(1);
            log.info("get free workerId:{}", workerId);
            preparedStatement = connection.prepareStatement("update global_worker_id set state=?,p_id=?,ip=?,last_start_time=?,memo=? where worker_id=? and state=?");
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, Integer.parseInt(pid));
            preparedStatement.setString(3, hostNameOrIP);
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(5, memo);
            preparedStatement.setInt(6, workerId);
            preparedStatement.setInt(7, 1);
            int updateCount = preparedStatement.executeUpdate();
            log.info("update workerId state sql:{}", "update global_worker_id set state=?,p_id=?,ip=?,last_start_time=?,memo=? where worker_id=? and state=?");
            if (updateCount != 1) {
                throw new IdGeneratorException("updateWorkerIdState fail when jvm start,current workerId:" + workerId);
            }

            connection.commit();
        } catch (Exception var12) {
            this.doError(connection, var12, (long)workerId);
        } finally {
            this.close(connection, preparedStatement, resultSet);
        }

        return workerId;
    }

    public int updateWorkerIdState2Free(int workerId, String ip, String pid) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int updateCount = 0;

        try {
            connection = this.dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("update global_worker_id set state=?,last_shutdown_time=? where worker_id=? and state=? and ip=? and p_id=?");
            preparedStatement.setInt(1, 1);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(3, workerId);
            preparedStatement.setInt(4, 1);
            preparedStatement.setString(5, ip);
            preparedStatement.setString(6, pid);
            updateCount = preparedStatement.executeUpdate();
            log.info("workerId shutdown sql:{}", "update global_worker_id set state=?,last_shutdown_time=? where worker_id=? and state=? and ip=? and p_id=?");
            if (updateCount != 1) {
                throw new IdGeneratorException("updateWorkerIdState fail when jvm start,current workerId:" + workerId);
            }

            connection.commit();
        } catch (Exception var12) {
            this.doError(connection, var12, -1L);
        } finally {
            this.close(connection, preparedStatement, (ResultSet)resultSet);
        }

        return updateCount;
    }



    private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (Objects.nonNull(connection)) {
                connection.close();
            }

            if (Objects.nonNull(preparedStatement)) {
                preparedStatement.close();
            }

            if (Objects.nonNull(resultSet)) {
                resultSet.close();
            }
        } catch (Exception var5) {
            log.error("close error:" + var5, var5);
        }

    }

    private void initConn() throws SQLException {
        this.dataSource.getConnection();
    }

    private void validate(DataSource dataSource) {
        if (Objects.isNull(dataSource)) {
            throw new NullPointerException("JdbcActor must has a datasource");
        }
    }

    private void doError(Connection connection, Exception e, long currentWorkerId) {
        log.info("createId fail of doError:{}", e);
        log.error("createId error:" + e, e);
        if (!Objects.isNull(connection)) {
            try {
                connection.rollback();
            } catch (SQLException var9) {
                log.error("rollback error:" + e, e);
            } finally {
                throw new IdGeneratorException("maybe query workerId success but update state fail,current workerId:" + currentWorkerId);
            }
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcActor() {
    }
}
