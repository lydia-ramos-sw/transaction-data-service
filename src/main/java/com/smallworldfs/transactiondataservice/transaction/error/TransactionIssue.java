package com.smallworldfs.transactiondataservice.transaction.error;

import com.smallworldfs.error.issue.DefaultIssueType;
import com.smallworldfs.error.issue.Issue;
import com.smallworldfs.error.issue.IssueType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionIssue implements Issue {

    TRANSACTION_NOT_FOUND("Transaction with id '{0}' could not be found",
            DefaultIssueType.NOT_FOUND),
    TRANSACTION_COULD_NOT_BE_CREATED("Transaction could not be created",
            DefaultIssueType.REQUEST_ERROR),
    TRANSACTION_COULD_NOT_BE_UPDATED("Transaction with id '{0}' could not be updated",
            DefaultIssueType.INTERNAL_ERROR),
    TRANSACTION_COULD_NOT_BE_PAID("Transaction with id '{0}' could not be paid",
            DefaultIssueType.INTERNAL_ERROR),
    CUSTOMER_INFO_ERROR("Problems when trying to recover info for customer with id '{0}'",
            DefaultIssueType.NOT_FOUND);

    private final String messageTemplate;
    private final IssueType type;
}
