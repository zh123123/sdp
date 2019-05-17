package xyz.huanz.pojo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training {
    @Id
    private String id;

    /**
     * 设备号
     */
    private String eno;

    /**
     * 时间
     */
    private Date time;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 速度
     */
    private BigDecimal speed;

    /**
     * 累计距离
     */
    private BigDecimal distance;

    /**
     * 心率
     */
    @Column(name = "heart_rate")
    private Integer heartRate;
    
    public String toString(int i) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return eno + " " + sdf.format(time) + " " + longitude + " " + latitude + " " + speed + " " + distance + " "
				+ heartRate + "\r\n";
	}
}