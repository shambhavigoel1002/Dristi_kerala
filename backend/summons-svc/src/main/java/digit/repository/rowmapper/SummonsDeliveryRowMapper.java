package digit.repository.rowmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.web.models.AdditionalFields;
import digit.web.models.ChannelName;
import digit.web.models.SummonsDelivery;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SummonsDeliveryRowMapper implements RowMapper<SummonsDelivery> {

    private final ObjectMapper objectMapper;

    @Autowired
    public SummonsDeliveryRowMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public SummonsDelivery mapRow(ResultSet rs, int rowNum) throws SQLException {
        SummonsDelivery summonsDelivery = new SummonsDelivery();

        summonsDelivery.setSummonsId(rs.getString("summons_id"));
        summonsDelivery.setCaseId(rs.getString("case_id"));
        summonsDelivery.setTenantId(rs.getString("tenant_id"));
        summonsDelivery.setDocType(rs.getString("doc_type"));
        summonsDelivery.setChannelName(ChannelName.valueOf(rs.getString("channel_name")));

        Map<String, String> channelDetails = new HashMap<>();
        try {
            channelDetails = objectMapper.readValue(rs.getString("additional_fields"), Map.class);
        } catch (JsonProcessingException e) {
            throw new SQLException(e);
        }
        summonsDelivery.setChannelDetails(channelDetails);

        summonsDelivery.setPaymentFees(rs.getString("payment_fees"));
        summonsDelivery.setPaymentTransactionId(rs.getString("payment_transaction_id"));
        summonsDelivery.setPaymentStatus(rs.getString("payment_status"));

        summonsDelivery.setIsAcceptedByChannel(rs.getBoolean("is_accepted_by_channel"));
        summonsDelivery.setChannelAcknowledgementId(rs.getString("channel_acknowledgement_id"));

        Date requestDate = rs.getDate("delivery_request_date");
        summonsDelivery.setDeliveryRequestDate(requestDate!= null ? requestDate.toLocalDate() : null);

        Date statusChangedDate = rs.getDate("delivery_status_changed_date");
        summonsDelivery.setDeliveryStatusChangedDate(statusChangedDate!= null ? statusChangedDate.toLocalDate() : null);

        summonsDelivery.setDeliveryStatus(rs.getString("status_of_delivery"));

        AdditionalFields additionalFields = new AdditionalFields();
        try {
            additionalFields = objectMapper.readValue(rs.getString("additional_fields"), AdditionalFields.class);
        } catch (JsonProcessingException e) {
            throw new SQLException(e);
        }
        summonsDelivery.setAdditionalFields(additionalFields);

        AuditDetails auditdetails = AuditDetails.builder()
                .createdBy(rs.getString("created_by"))
                .createdTime(rs.getLong("created_time"))
                .lastModifiedBy(rs.getString("last_modified_by"))
                .lastModifiedTime(rs.getLong("last_modified_time"))
                .build();
        summonsDelivery.setAuditDetails(auditdetails);

        summonsDelivery.setRowVersion(rs.getInt("row_version"));
        return summonsDelivery;
    }
}
