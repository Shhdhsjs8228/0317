import java.util.ArrayList;
import java.util.Scanner;

// --- 1. 例外處理概念 (Exception Handling Concept) ---
// 自定義例外類別，用來處理登入失敗的情況 [cite: 100, 103]
class LoginFailedException extends Exception {
    public LoginFailedException(String message) {
        super(message);
    }
}

// --- 2. 繼承 (Inheritance) ---
// 父類別 Person [cite: 44, 46]
class Person {
    protected String name; // 使用 protected 讓子類別 User 可以存取 [cite: 47]
}

// --- 3. 封裝 (Encapsulation) ---
// User 類別繼承自 Person [cite: 49]
class User extends Person {
    private String username; // 使用 private 保護資料，外部不能直接存取 [cite: 37, 43]
    private String password; // 使用 private 保護資料 [cite: 38]

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    // 透過 public 方法（Getter）來讀取私有資料 [cite: 39]
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void showRoleInfo() {
        System.out.println("Role: General User");
    }
}

// --- 4. 多型 (Polymorphism) ---
// StudentUser 繼承自 User [cite: 62]
class StudentUser extends User {
    public StudentUser(String name, String username, String password) {
        // 使用 super() 呼叫父類別的建構子 [cite: 57, 59]
        super(name, username, password);
    }

    @Override // 覆寫父類別方法形成多型 [cite: 63, 69]
    public void showRoleInfo() {
        System.out.println("Student Login");
    }
}

// --- 5. 系統主程式 ---
// 注意：檔案名稱必須叫做 LoginSystem.java
public class LoginSystem {
    // 使用 ArrayList 動態儲存多個 User 物件 [cite: 71, 73]
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        // 初始化測試資料 [cite: 72]
        users.add(new User("Admin", "admin", "1234"));
        users.add(new StudentUser("Alice", "alice_s", "pass"));

        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n--- Terminal Login System ---");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choice: ");
            
            // 流程控制：使用 switch 處理選單 [cite: 86]
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    handleLogin(sc);
                    break;
                case 2:
                    System.out.println("Goodbye!");
                    System.exit(0);
            }
        }
    }

    private static void handleLogin(Scanner sc) {
        System.out.print("Username: ");
        String u = sc.next();
        System.out.print("Password: ");
        String p = sc.next();

        // 完整的例外處理：try-catch-finally [cite: 91, 94]
        try {
            User user = login(u, p);
            System.out.println("Login Success! Welcome, " + user.name);
            user.showRoleInfo(); // 這裡會展現「多型」，不同角色顯示不同資訊 [cite: 102]
        } 
        catch (LoginFailedException e) {
            // catch 會捕捉並處理執行錯誤 [cite: 97]
            System.out.println("Error: " + e.getMessage());
        } 
        finally {
            // finally 區塊內的程式碼一定會執行 [cite: 99]
            System.out.println("----------------------------");
        }
    }

    // 拋出例外：使用 throws 將錯誤交給呼叫者處理 [cite: 100, 101]
    public static User login(String u, String p) throws LoginFailedException {
        for (User user : users) {
            if (user.getUsername().equals(u) && user.getPassword().equals(p)) {
                return user;
            }
        }
        // 如果沒找到匹配的帳密，則拋出例外 [cite: 100]
        throw new LoginFailedException("Login Failed: Incorrect username or password.");
    }
}