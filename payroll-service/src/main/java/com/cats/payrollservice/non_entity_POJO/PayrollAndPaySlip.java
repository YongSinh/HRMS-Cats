package com.cats.payrollservice.non_entity_POJO;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "GetPayrollByRefNo2",
                procedureName = "GetPayrollByRefNo2",
                resultSetMappings = "GetPayrollMapping"
        ),
        @NamedStoredProcedureQuery(
                name = "GetListPayroll",
                procedureName = "GetListPayroll",
                resultSetMappings = "GetPayrollMapping"
        ),
        @NamedStoredProcedureQuery(
                name = "GetPayrollByCreateDate",
                procedureName = "GetPayrollByCreateDate",
                resultSetMappings = "GetPayrollMapping"
        ),
        @NamedStoredProcedureQuery(
                name = "GetPayrollWithTaxForUser",
                procedureName = "GetPayrollWithTaxForUser",
                resultSetMappings = "GetPayrollMapping"
        )
})
@SqlResultSetMapping(
        name = "GetPayrollMapping",
        classes = @ConstructorResult(
                targetClass = PayrollAndPaySlip.class,
                columns = {
                        @ColumnResult(name = "empId", type = Long.class),
                        @ColumnResult(name = "ref_no", type = String.class),
                        @ColumnResult(name = "salary", type = Double.class),
                        @ColumnResult(name = "type", type = String.class),
                        @ColumnResult(name = "status", type = String.class),
                        @ColumnResult(name = "allowances", type = String.class),
                        @ColumnResult(name = "total_allowances", type = Double.class),
                        @ColumnResult(name = "deductions", type = String.class),
                        @ColumnResult(name = "total_deductions", type = Double.class),
                        @ColumnResult(name = "total_earning", type = Double.class),
                        @ColumnResult(name = "net", type = Double.class),
                        @ColumnResult(name = "date", type = String.class),
                        @ColumnResult(name = "tax_rate", type = Double.class)
                }
        )
)
@Data
@NoArgsConstructor
public class PayrollAndPaySlip {
    @Id
    private String ref_no;
    private Long empId;
    private Double salary;
    private String type;
    private String status;
    private String allowances;
    private Double total_allowances;
    private String deductions;
    private Double total_deductions;
    private Double total_earning;
    private Double net;
    private String date;
    private Double tax_rate;
}
