package CourseWork3stCourse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Sock {
    private final Color color;
    private final Size size;
    private final int cottonPart;
}