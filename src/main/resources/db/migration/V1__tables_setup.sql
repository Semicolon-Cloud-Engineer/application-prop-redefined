CREATE TABLE IF NOT EXISTS karrabo_business_party (
                                      karrabo_business_party_id bigserial PRIMARY KEY,
                                      full_name VARCHAR(255),
                                      created_at timestamp(6),
                                      business_party_type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS karrabo_organization (
                                     organization_id VARCHAR(255) PRIMARY KEY,
                                     company_number VARCHAR(255),
                                     date_of_registration VARCHAR(255),
                                     business_type VARCHAR(255),
                                     head_office_address VARCHAR(500),
                                     operation_status VARCHAR(255),
                                     name_of_director VARCHAR(255),
                                     karrabo_business_party_id bigserial CONSTRAINT fk_org_to_business_party_constraint
                                        REFERENCES karrabo_business_party(karrabo_business_party_id)
                                            ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS karrabo_platform_user (
                                     user_id VARCHAR(255) PRIMARY KEY,
                                     first_name VARCHAR(500),
                                     last_name VARCHAR(500),
                                     email VARCHAR(500) UNIQUE,
                                     karrabo_business_party_id bigserial CONSTRAINT fk_platform_user_to_business_party_constraint
                                        REFERENCES karrabo_business_party(karrabo_business_party_id)
                                            ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS organization_employee (
                                    id bigserial PRIMARY KEY,
                                    employee_id VARCHAR(255) CONSTRAINT fk_org_employee_employee
                                        REFERENCES karrabo_platform_user(user_id)
                                            ON DELETE RESTRICT,
                                    organization_id VARCHAR(255) CONSTRAINT fk_org_employee_organization_constraint
                                        REFERENCES karrabo_organization(organization_id)
                                            ON DELETE RESTRICT
);
