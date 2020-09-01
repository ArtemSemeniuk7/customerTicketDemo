package net.customer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "RequestTable class")
@Component
@Getter
@Setter
@Entity
public class RequestTable {
    @ApiModelProperty(value = "request id")
    @Id
    private Long requestId;

    @ApiModelProperty(value = "client id")
    @NotNull
    @Column
    private Long clientId;

    @ApiModelProperty(value = "route number")
    @NotNull
    @Column(unique = true)
    private Long routeNumber;

    @ApiModelProperty(value = "date")
    @NotNull
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ApiModelProperty(value = "executionStatus")
    @Column
    private String executionStatus;

    @ApiModelProperty(value = "requestStatus")
    @Column
    private int requestStatus;
}
