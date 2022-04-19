import com.accp.server.dto.CategoryDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TestCC {
  private static final Logger LOG = LoggerFactory.getLogger(TestCC.class);
  @Test
  void show(){
    ArrayList<String> list = new ArrayList<>();
    list.add("Hello");
    list.add("bit");
    list.add("hello");
    list.add("lambda");
    CategoryDto categoryDto = new CategoryDto();
    CategoryDto categoryDto2 = new CategoryDto();
    List<CategoryDto> categoryDtoList = new ArrayList<>();
    categoryDto.setName("我1");
    categoryDto2.setName("可");
    //categoryDtoList.set(0,categoryDto);
    categoryDtoList.add(0, categoryDto);
    categoryDtoList.add(0, categoryDto2);
    List<String> listString = categoryDtoList.stream().map(CategoryDto::getName).sorted().collect(Collectors.toList());
    listString.forEach(b -> System.out.println(b));
    //Collectors.groupingBy(categoryDto);
  }
  @Test
  void xx(){

  }

}
