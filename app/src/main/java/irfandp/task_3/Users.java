package irfandp.task_3;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 9/22/2016.
 */
public class Users {

    @SerializedName("users")
    public List<UserItem> users;
    public List<UserItem> getUsers() { return users; }
    public void setUsers(List<UserItem> users) { this.users = users; }
    public Users(List<UserItem> users) { this.users = users; }

    public class UserItem {

        private String name;

        private String email;

        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
