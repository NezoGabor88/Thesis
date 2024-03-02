package iit.unimiskolc.FRI.business;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static iit.unimiskolc.FRI.business.MtmtDataHandler.MAX_MTMT_QUERY_PAGE_SIZE;

@Data
@Validated
@Component
public class MtmtConfig {
    @Value("${mtmt.truststore.path}")
    @NotNull
    @NotEmpty
    String mtmtTruststorePath;
    @Value("${mtmt.truststore.password}")
    @NotNull
    @NotEmpty
    String mtmtTruststorePassword;
    @Value("${mtmt.url}")
    @NotNull
    @NotEmpty
    String mtmtUrl;

    @Value("${mtmt.query.pageSize}")
    @Max(MAX_MTMT_QUERY_PAGE_SIZE)
    @Min(0)
    @NotNull
    Integer mtmtQueryPageSize;
}
