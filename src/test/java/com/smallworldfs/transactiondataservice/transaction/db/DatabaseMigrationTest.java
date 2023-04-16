package com.smallworldfs.transactiondataservice.transaction.db;

import static com.smallworldfs.starter.relationaldbtest.asserts.DataSourceAssert.assertThatDataSource;

import com.smallworldfs.starter.relationaldb.autoconfigure.RelationalDbAutoConfiguration;
import com.smallworldfs.starter.relationaldbtest.RelationalDbTest;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



@RelationalDbTest(classes = RelationalDbAutoConfiguration.class)
public class DatabaseMigrationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void create_Transaction_table() {
        assertThatDataSource(dataSource)
                .schema("transaction")
                .table("transaction")
                .exists();
    }

    @Test
    void creates_columns_in_transaction_table() {
        assertThatDataSource(dataSource)
                .schema("transaction")
                .table("transaction")
                .hasColumns("id",
                        "sending_principal",
                        "payout_principal",
                        "fees",
                        "commission",
                        "agent_commission",
                        "sender_id",
                        "beneficiary_id",
                        "status");
    }
}
