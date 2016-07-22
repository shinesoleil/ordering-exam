package com.thoughtworks.ketsu.support;

import java.util.*;

public class TestHelper {
    private static int auto_increment_key = 1;
    public static Map<String, Object> deployment(String appName, String releaseId) {
        return new HashMap<String, Object>() {{
            put("app", String.format("http://service-api.tw.com/apps/%s", appName));
            put("release", String.format("http://service-api.tw.com/apps/%s/releases/%s", appName, releaseId));
        }};
    }

    public static Map<String, Object> stackMap(String id, String name) {
        Map<String, Object> stackMap = new HashMap<String, Object>() {{
            put("id", id);
            put("name", name);
        }};
        return stackMap;
    }



//    public static User userForTest(String id, String name, UserRole role) {
//        String password_123 = "$2a$04$DbgJbGA4dkQSzAvXvJcGBOv5kHuMBzrWfne3x3Cx4JQv4IJcxtBIW";
//        return new User(new UserId(id), name, name + "@tw.com", role, password_123);
//    }
//
//    public static User userFixture(UserRepository userRepository, UserRole role) {
//        final String id = new Integer(auto_increment_key++).toString();
//        User user = userForTest(id, "name-" + id, role);
//        userRepository.save(user);
//        return user;
//    }

    public static Map<String, Object> productMap() {
        return new HashMap<String, Object>() {{
            put("name", "desk");
            put("description", "black");
            put("price", 610);
        }};
    }

    public static Map<String, Object> userMap() {
        return new HashMap<String, Object>() {{
            put("name", "firstUser");
        }};
    }

    public static Map<String, Object> orderMap(int productId) {
        return new HashMap<String, Object>() {{
            put("name", "firstOrder");
            put("address", "Beijing");
            put("phone", "13099999999");
            put("order_items", orderItemMapList(productId));
        }};
    }

    public static List<Map<String, Object>> orderItemMapList(int productId) {
        Map<String, Object> item =  new HashMap<String, Object>() {{
            put("product_id", productId);
            put("quantity", 2);
        }};

        return new ArrayList<Map<String, Object>>() {{
            add(item);
        }};
    }
}
