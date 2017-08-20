import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by adavi on 15.08.2017.
 */
public class StreamSample {

    enum Role{
        ADMIN,USER,GUEST;
    }

    static class User{
        private long id;
        private String name;
        private  Role role;

        public User(long id, String name, Role role) {
            this.id = id;
            this.name = name;
            this.role = role;

        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Role getRole() {
            return role;
        }

        @Override
        public String toString() {
            return "User{" + "id=" + id +", name='" + name + '}';
        }
        /**--------------------- Приклад того як проста вибірка з колекції була реалізована на Java 7------------------**/

        public void testSeampelStreamJava7() {
            Collection<User> users = Arrays.asList(
                    new User(1, "Oleg Kok", Role.USER),
                    new User(5, "Taras Kuk", Role.USER),
                    new User(78, "Victor Kramp", Role.GUEST),
                    new User(3, "Mark Tven", Role.USER),
                    new User(10, "Tamara Antonivna", Role.ADMIN)
            );

            /**----------- Filtring - тут ми фільтруємо дані які нам потрібні далі -------------------**/
            List<User> filtred = new LinkedList<>();
            for (User user : users) {
                if (user.getRole() == Role.USER){
                    filtred.add(user);
                }

            }
            /**-------------- А тут ми сортуємо результати фільтрування  по певному критетію ----------------**/
            /**------------В метод Collections.sort ми передаємо колекцію filtred та компаратор який у нас
             * буде сортувати колекцію по вказаному параметру   -  id --------------**/
            Collections.sort(filtred, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                   return Long.compare(o1.getId(), o2.getId());
                }
            });

            List<User> names = new LinkedList<>();
            for (User user : filtred) {
                names.add(user);
            }

            /** --------------- Отримали б такий результат
            assertEquals("[Oleg Kok,Taras Kuk,Mark Tven]", names.toString()); -----------**/


            /** Тепер це вилядає так на java 8 **/


    }
        public void testSeampelStreamJava8() {
            Collection<User> users = Arrays.asList(
                    new User(1, "Oleg Kok", Role.USER),
                    new User(5, "Taras Kuk", Role.USER),
                    new User(78, "Victor Kramp", Role.GUEST),
                    new User(3, "Mark Tven", Role.USER),
                    new User(10, "Tamara Antonivna", Role.ADMIN)
            );

            /**-------------- З допомого Stream все стало простіше ----------------**/
            List<String> names = users.stream()
                    /**---Спочатку ми фільтруємо колекцію
                     * Якщо у user роль Role.USER то ідемо далі
                     */
                    .filter(user -> user.getRole() == Role.USER)
                    /**--- тут сортуємо юзерів з роллю  Role.USER по ід**/
                    .sorted((o1, o2) -> Long.compare(o2.getId(),o1.getId()))
                    /** Виводимо їхні імена**/
                    .map(user -> user.getName())
                    /** в ліст **/
                    /** -- Терминальна операція  - приводить до обрахунків **/
                    .collect(toList());



                    /**--------------------- МОЖНА ЩЕ СКОРОТИТИ КОД---------------------------- **/
            List<String> newNames = users.stream()
                    .filter(user -> getRole() == Role.USER)
                    .sorted(Comparator.comparing(User ::getId).reversed())
                    .map(User :: getName)
                    .collect(Collectors.toList());

        }

}
}
