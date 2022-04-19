////import com.mysql.jdbc.log.Log;
//
//import com.accp.server.dto.CategoryDto;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.stream.Collectors;
//
//
//public class Test {
//  public static String show(String gs) {
//    String[] args = gs.split("");
//
//    for (int i = 0; i < args.length; i++) {
//      int b = 0;
////System.out.println(args[i]);
//      if (i < (args.length - 1) && args[i + 1].equals(args[i])) {
//        if (i + 2 < (args.length) && args[i + 2].equals(args[i])) {
//          System.out.println("重复语句是:" + i + args[i]);
//          ++b;
//        }
//      }
//    }
//    return "";
//  }
//
//  private static final Logger LOG = LoggerFactory.getLogger(System.class);
//
//  public static void main(String[] args) {
//    ArrayList<String> list = new ArrayList<>();
//    list.add("Hello");
//    list.add("bit");
//    list.add("hello");
//    list.add("lambda");
//    CategoryDto categoryDto = new CategoryDto();
//    CategoryDto categoryDto2 = new CategoryDto();
//    List<CategoryDto> categoryDtoList = new ArrayList<>();
//    categoryDto.setName("我1");
//    categoryDto2.setName("可");
//    //categoryDtoList.set(0,categoryDto);
//    categoryDtoList.add(0, categoryDto);
//    categoryDtoList.add(0, categoryDto2);
//    List<String> listString = categoryDtoList.stream().map(CategoryDto::getName).sorted().collect(Collectors.toList());
//    listString.forEach(b -> System.out.println(b));
//  }
//}
