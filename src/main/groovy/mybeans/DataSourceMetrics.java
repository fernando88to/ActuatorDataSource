package mybeans;


import grails.util.Holders;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.lang.NonNull;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class DataSourceMetrics implements MeterBinder {

    @Override
    public void bindTo(@NonNull MeterRegistry registry) {
        Gauge.builder("tomcat.jdbc.pool.iddle", this, DataSourceMetrics::getIddle).register(registry);
        Gauge.builder("tomcat.jdbc.pool.maxiddle", this, DataSourceMetrics::getMaxIddle).register(registry);
        Gauge.builder("tomcat.jdbc.pool.active", this, DataSourceMetrics::getActive).register(registry);
        Gauge.builder("tomcat.jdbc.pool.maxactive", this, DataSourceMetrics::getMaxActive).register(registry);
        Gauge.builder("tomcat.jdbc.pool.size", this, DataSourceMetrics::getSize).register(registry);



    }

    private DataSource getDataSource(){
        DataSource ds = (DataSource) Holders.getGrailsApplication().getMainContext().getBean("dataSource");
        return ds;
    }
    private org.apache.tomcat.jdbc.pool.DataSource getDataSourceJdbcTomcat(DataSource dataSource){

        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            TransactionAwareDataSourceProxy ta = (TransactionAwareDataSourceProxy) dataSource;
            if (ta.getTargetDataSource() instanceof LazyConnectionDataSourceProxy) {
                LazyConnectionDataSourceProxy lcds = (LazyConnectionDataSourceProxy) ta.getTargetDataSource();
                if (lcds.getTargetDataSource() instanceof org.apache.tomcat.jdbc.pool.DataSource) {
                    org.apache.tomcat.jdbc.pool.DataSource dsTomcat = (org.apache.tomcat.jdbc.pool.DataSource) lcds.getTargetDataSource();
                    return dsTomcat;

                }

            }
        }
        return null;

    }
    public Integer getIddle() {
        DataSource ds = getDataSource();
        org.apache.tomcat.jdbc.pool.DataSource dataSourceJdbcTomcat = getDataSourceJdbcTomcat(ds);
        if (dataSourceJdbcTomcat != null) {
            return dataSourceJdbcTomcat.getNumIdle();
        }
        return 0;
    }

    public Integer getMaxIddle() {
        DataSource ds = getDataSource();
        org.apache.tomcat.jdbc.pool.DataSource dataSourceJdbcTomcat = getDataSourceJdbcTomcat(ds);
        if (dataSourceJdbcTomcat != null) {
            return dataSourceJdbcTomcat.getMaxActive();
        }
        return 0;
    }

    public Integer getActive() {
        DataSource ds = getDataSource();
        org.apache.tomcat.jdbc.pool.DataSource dataSourceJdbcTomcat = getDataSourceJdbcTomcat(ds);
        if (dataSourceJdbcTomcat != null) {
            return dataSourceJdbcTomcat.getActive();
        }
        return 0;
    }
    public Integer getMaxActive() {
        DataSource ds = getDataSource();
        org.apache.tomcat.jdbc.pool.DataSource dataSourceJdbcTomcat = getDataSourceJdbcTomcat(ds);
        if (dataSourceJdbcTomcat != null) {
            return dataSourceJdbcTomcat.getMaxActive();
        }
        return 0;
    }

    public Integer getSize() {
        DataSource ds = getDataSource();
        org.apache.tomcat.jdbc.pool.DataSource dataSourceJdbcTomcat = getDataSourceJdbcTomcat(ds);
        if (dataSourceJdbcTomcat != null) {
            return dataSourceJdbcTomcat.getSize();
        }
        return 0;
    }






}
