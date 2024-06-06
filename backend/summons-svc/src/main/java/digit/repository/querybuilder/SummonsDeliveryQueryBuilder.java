package digit.repository.querybuilder;

import digit.web.models.SummonsDeliverySearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SummonsDeliveryQueryBuilder {

    private final String BASE_APPLICATION_QUERY = "SELECT summons_id, case_id, tenant_id, doc_type, channel_name, payment_fees, payment_transaction_id, payment_status, channel_details, is_accepted_by_channel, channel_acknowledgement_id, delivery_request_date, delivery_status_changed_date, additional_fields, created_by, last_modified_by, created_time, last_modified_time, row_version ";

    private static final String FROM_TABLES = " FROM summons_delivery ";

    public String getSummonsQuery(SummonsDeliverySearchCriteria searchCriteria, List<String> preparedStmtList) {
        StringBuilder query = new StringBuilder(BASE_APPLICATION_QUERY);
        query.append(FROM_TABLES);

        if (!ObjectUtils.isEmpty(searchCriteria.getOrderId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" summons_delivery_unique_id = ? ");
            preparedStmtList.add(searchCriteria.getOrderId());
        }
        if(!ObjectUtils.isEmpty(searchCriteria.getSummonsId())){
            addClauseIfRequired(query, preparedStmtList);
            query.append(" summons_id = ? ");
            preparedStmtList.add(searchCriteria.getSummonsId());
        }

        return query.toString();
    }

    private void addClauseIfRequired(StringBuilder query, List<String> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
    }
}
