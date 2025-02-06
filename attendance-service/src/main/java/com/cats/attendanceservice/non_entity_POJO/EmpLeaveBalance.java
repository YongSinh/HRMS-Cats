package com.cats.attendanceservice.non_entity_POJO;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "GetEmpLeaveBlance",
                procedureName = "GetEmpLeaveBlance",
                resultSetMappings = "GetPayrollMapping"
        )
})
@SqlResultSetMapping(
        name = "GetPayrollMapping",
        classes = @ConstructorResult(
                targetClass = EmpLeaveBalance.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "empid", type = Long.class),
                        @ColumnResult(name = "SI_L", type = Long.class),
                        @ColumnResult(name = "SP_L", type = Long.class),
                        @ColumnResult(name = "AN_L", type = Long.class),
                        @ColumnResult(name = "MT_L", type = Long.class),

                }
        )
)
@Data
@NoArgsConstructor
public class EmpLeaveBalance {
    @Id
    private Long id;
    private Long empid;
    private Long SI_L;
    private Long SP_L;
    private Long AN_L;
    private Long MT_L;
}
