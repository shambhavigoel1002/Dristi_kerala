CREATE TABLE opt_out
(

    id                                  character varying(64),
    individual_id                       character varying(64),
    judge_id                            character varying(64),
    case_id                             character varying(64),
    reschedule_request_id               character varying(64),
    opt_out_dates                       jsonb,
    created_by                          character varying(64),
    created_time                        bigint,
    last_modified_by                    character varying(64),
    last_modified_time                  bigint,
    row_version                         bigint,
    tenant_id                           character varying(64),

    CONSTRAINT pk_opt_out_id PRIMARY KEY (id)

);