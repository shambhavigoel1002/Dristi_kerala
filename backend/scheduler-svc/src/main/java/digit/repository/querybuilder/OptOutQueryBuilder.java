package digit.repository.querybuilder;


import digit.helper.QueryBuilderHelper;
import digit.web.models.OptOutSearchCriteria;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class OptOutQueryBuilder {

    @Autowired
    private QueryBuilderHelper queryBuilderHelper;


    private final String BASE_APPLICATION_QUERY = "SELECT  oo.id ,oo.individual_id ,oo.judge_id ,oo.case_id ,oo.reschedule_request_id ,oo.opt_out_dates , oo.created_by,oo.last_modified_by,oo.created_time,oo.last_modified_time, oo.row_version ";

    private static final String FROM_TABLES = " FROM opt_out oo ";

    private final String ORDER_BY = " ORDER BY ";

    private final String GROUP_BY = " GROUP BY ";

    private final String LIMIT_OFFSET = " LIMIT ? OFFSET ?";

    public String getOptOutQuery(OptOutSearchCriteria optOutSearchCriteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(BASE_APPLICATION_QUERY);
        query.append(FROM_TABLES);

        if (!CollectionUtils.isEmpty(optOutSearchCriteria.getIds())) {
            queryBuilderHelper.addClauseIfRequired(query, preparedStmtList);
            query.append(" oo.id IN ( ").append(queryBuilderHelper.createQuery(optOutSearchCriteria.getIds())).append(" ) ");
            queryBuilderHelper.addToPreparedStatement(preparedStmtList, optOutSearchCriteria.getIds());
        }

        if (!ObjectUtils.isEmpty(optOutSearchCriteria.getJudgeId())) {
            queryBuilderHelper.addClauseIfRequired(query, preparedStmtList);
            query.append(" oo.judge_id = ? ");
            preparedStmtList.add(optOutSearchCriteria.getJudgeId());

        }
        if (!ObjectUtils.isEmpty(optOutSearchCriteria.getCaseId())) {
            queryBuilderHelper.addClauseIfRequired(query, preparedStmtList);
            query.append(" oo.case_id = ? ");
            preparedStmtList.add(optOutSearchCriteria.getCaseId());

        }

        if (!ObjectUtils.isEmpty(optOutSearchCriteria.getIndividualId())) {
            queryBuilderHelper.addClauseIfRequired(query, preparedStmtList);
            query.append(" oo.individual_id = ? ");
            preparedStmtList.add(optOutSearchCriteria.getIndividualId());

        }
        if (!ObjectUtils.isEmpty(optOutSearchCriteria.getRescheduleRequestId())) {
            queryBuilderHelper.addClauseIfRequired(query, preparedStmtList);
            query.append(" oo.reschedule_request_id = ? ");
            preparedStmtList.add(optOutSearchCriteria.getRescheduleRequestId());

        }
        if (!ObjectUtils.isEmpty(optOutSearchCriteria.getTenantId())) {
            queryBuilderHelper.addClauseIfRequired(query, preparedStmtList);
            query.append(" oo.tenant_id = ? ");
            preparedStmtList.add(optOutSearchCriteria.getTenantId());

        }


        return query.toString();


    }
}
