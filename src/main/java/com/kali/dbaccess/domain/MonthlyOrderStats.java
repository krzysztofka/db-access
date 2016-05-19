package com.kali.dbaccess.domain;

import com.google.common.base.Objects;

import javax.persistence.*;

@Entity
@SqlResultSetMapping(
        name = "monthlyOrderStatsMapping",
        entities = {
            @EntityResult(entityClass = MonthlyOrderStats.class, fields = {
                @FieldResult(name="year", column="year"),
                @FieldResult(name="month", column="month"),
                @FieldResult(name="avg", column="r_avg"),
                @FieldResult(name="sum", column="r_sum"),
                @FieldResult(name="median", column="r_median")
            })
        }
)
@NamedNativeQuery(
        name = "monthlyOrderStatsQuery",
        query = "select year(t.order_date) as year, month(t.order_date) as month, avg(t.total) as r_avg, " +
                "sum(t.total) as r_sum, median(t.total) as r_median " +
                "from (select o.id as id, sum(i.quantity * p.price) as total, o.order_date " +
                "from orders o join order_items i on o.id = i.order_id join products p on p.id = i.product_id " +
                "group by o.id) t group by year(t.order_date), month(t.order_date)",
        resultClass = MonthlyOrderStats.class,
        resultSetMapping="monthlyOrderStatsMapping"
)
public class MonthlyOrderStats {

    @Id
    private int month;

    private int year;

    private long sum;

    private double avg;

    private long median;

    public MonthlyOrderStats() {
    }

    public MonthlyOrderStats(int month, int year, long sum, double avg, long median) {
        this.month = month;
        this.year = year;
        this.sum = sum;
        this.avg = avg;
        this.median = median;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public long getSum() {
        return sum;
    }

    public double getAvg() {
        return avg;
    }

    public long getMedian() {
        return median;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public void setMedian(long median) {
        this.median = median;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthlyOrderStats that = (MonthlyOrderStats) o;
        return month == that.month &&
                year == that.year &&
                sum == that.sum &&
                Double.compare(that.avg, avg) == 0 &&
                median == that.median;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(month, year, sum, avg, median);
    }
}
