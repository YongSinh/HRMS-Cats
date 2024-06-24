package cats.com.notificationservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListEmpByEmpIdEvent {
    private Long emId;
}
