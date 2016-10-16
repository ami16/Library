package edu.cursor.library.user.utils;
import java.util.Arrays;
import java.util.stream.Collectors;

import edu.cursor.library.user.enums.Role;


public class RoleUtil {
	public static Role chooseRole(String srole) {
        Boolean checkRole = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toList())
                .stream()
                .anyMatch(srole::equals);
        if (checkRole) {
            Role role = Role.valueOf(srole);
            return role;
        } else {
            System.out.println("Role:" + srole + " doesn't exist!");
            return chooseRole(srole);
        }
    }

}
