package controller;

import model.Operation;
import model.Role;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.OperationService;
import service.RoleService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private OperationService operationService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String indexPage() {
        return "system/index";
    }

    @RequestMapping("/operation")
    public String operationListPage(Model model,
                                    @RequestParam(value = "action", defaultValue = "") String action,
                                    @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        model.addAttribute("operationList", operationService.findOperations(action, pageIndex, pageSize));
        model.addAttribute("searchAction", action);
        model.addAttribute("pageIndex", pageIndex);
        return "system/operationList";
    }

    @RequestMapping("/newOperation")
    public String newOperationPage(Model model) {
        model.addAttribute("action", "New");
        model.addAttribute("actionUrl", "/system/saveOperation");
        model.addAttribute("operation", new Operation());
        return "system/operation";
    }

    @RequestMapping("/saveOperation")
    public String saveOperation(Model model,
                                @ModelAttribute Operation operation,
                                BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            operationService.saveOperation(operation);
            return "redirect:/system/operation";
        } else {
            model.addAttribute("error", "CAN NOT SAVE");
            return "system/operation";
        }
    }

    @RequestMapping("/editOperation")
    public String editOperationPage(Model model, @RequestParam("id") Integer id) {
        model.addAttribute("action", "Edit");
        model.addAttribute("actionUrl", "/system/updateOperation");
        model.addAttribute("operation", operationService.findOperationById(id));
        return "system/operation";
    }

    @RequestMapping("/updateOperation")
    public String updateOperation(Model model,
                                  @ModelAttribute Operation operation,
                                  BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            operationService.updateOperation(operation);
            return "redirect:/system/operation";
        } else {
            model.addAttribute("error", "CAN NOT UPDATE");
            return "system/operation";
        }
    }

    //// TODO: 2016/10/21 delete operations
    @RequestMapping("/deleteOperations")
    public String deleteOperations(@ModelAttribute int[] ids, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            for (int i = 0; i < ids.length; ++i) {
                Operation opToDel = new Operation();
                opToDel.setId(ids[i]);
                operationService.deleteOperation(opToDel);
            }
        }
        return "redirect:/system/operation";
    }

    @RequestMapping("/role")
    public String roleListPage(Model model,
                               @RequestParam(value = "roleName", defaultValue = "") String roleName,
                               @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        model.addAttribute("roleName", roleName);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("roles", roleService.findRoles(roleName, pageIndex, pageSize));
        return "system/roleList";
    }

    @RequestMapping("/newRole")
    public String newRolePage(Model model) {
        model.addAttribute("action", "New");
        model.addAttribute("actionUrl", "/system/saveRole");
        model.addAttribute("role", new Role());
        Map<String, List<Operation>> groupedOperations = operationService.findAllOperations()
                .stream()
                .collect(groupingBy(p -> p.getController(), mapping((Operation o) -> o, toList())));
        model.addAttribute("operationGroups", groupedOperations);
        return "system/role";
    }

    @RequestMapping("/saveRole")
    public String saveRole(Model model,
                           @RequestParam("id") Integer id,
                           @RequestParam("name") String name,
                           @RequestParam(value = "operations", required = false) Integer[] opIds,
                           @RequestParam("remark") String remark) {
        if (!StringUtils.hasText(name) || !name.toUpperCase().startsWith("ROLE_")) {
            model.addAttribute("error", "role name must start with \"ROLE_\"!");
            model.addAttribute("action", "New");
            model.addAttribute("actionUrl", "/system/saveRole");
            model.addAttribute("role", new Role());
            Map<String, List<Operation>> groupedOperations = operationService.findAllOperations()
                    .stream()
                    .collect(groupingBy(p -> p.getController(), mapping((Operation o) -> o, toList())));
            model.addAttribute("operationGroups", groupedOperations);
            return "system/role";
        }
        Role role = new Role();
        role.setName(name);
        role.setRemark(remark);
        if (opIds != null) {
            List<Operation> operations = new ArrayList<>();
            for (int i = 0; i < opIds.length; ++i) {
                Operation op = operationService.findOperationById(opIds[i]);
                op.getRoles().add(role);
                operations.add(op);
            }
            role.setOperations(operations);
        }
        roleService.saveRole(role);
        return "redirect:/system/role";
    }

    @RequestMapping("/editRole")
    public String editRolePage(Model model, @RequestParam("id") Integer id) {
        model.addAttribute("action", "Edit");
        model.addAttribute("actionUrl", "/system/updateRole");
        model.addAttribute("role", roleService.findRoleById(id));
        Map<String, List<Operation>> groupedOperations = operationService.findAllOperations()
                .stream()
                .collect(groupingBy(p -> p.getController(), mapping((Operation o) -> o, toList())));
        model.addAttribute("operationGroups", groupedOperations);
        return "system/role";
    }

    @RequestMapping("/updateRole")
    public String updateRole(Model model,
                             @RequestParam("id") Integer id,
                             @RequestParam("name") String name,
                             @RequestParam(value = "operations", required = false) Integer[] opIds,
                             @RequestParam("remark") String remark) {
        if (!StringUtils.hasText(name) || !name.toUpperCase().startsWith("ROLE_")) {
            model.addAttribute("error", "role name must start with \"ROLE_\"!");
            model.addAttribute("action", "Edit");
            model.addAttribute("actionUrl", "/system/updateRole");
            model.addAttribute("role", roleService.findRoleById(id));
            Map<String, List<Operation>> groupedOperations = operationService.findAllOperations()
                    .stream()
                    .collect(groupingBy(p -> p.getController(), mapping((Operation o) -> o, toList())));
            model.addAttribute("operationGroups", groupedOperations);
            return "system/role";
        }
        Role role = roleService.findRoleById(id);
        role.setName(name);
        role.setRemark(remark);
        List<Operation> operations = new ArrayList<>();
        if (opIds != null) {
            for (int i = 0; i < opIds.length; ++i) {
                Operation operation = operationService.findOperationById(opIds[i]);
                operations.add(operation);
            }
        }
        role.setOperations(operations);
        roleService.updateRole(role);
        return "redirect:/system/role";
    }

    //// TODO: 2016/10/21 add delete roles

    @RequestMapping("/user")
    public String userListPage(Model model,
                               @RequestParam(name = "username", defaultValue = "") String username,
                               @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
                               @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        model.addAttribute("username", username);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("users", userService.findUsers(username, pageIndex, pageSize));
        return "system/userList";
    }

    @RequestMapping("/newUser")
    public String newUserPage(Model model) {
        model.addAttribute("action", "New");
        model.addAttribute("actionUrl", "/system/saveUser");
        model.addAttribute("user", new User());
        model.addAttribute("roleList", roleService.findAllRoles());
        return "system/user";
    }

    @RequestMapping("/saveUser")
    public String saveUser(Model model,
                           @RequestParam("id") Integer id,
                           @RequestParam("name") String name,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "enabled", defaultValue = "false") Boolean enabled,
                           @RequestParam(value = "roles", required = false) Integer[] roleIds) throws Exception {
        List<Role> roles = new ArrayList<>();
        for (Integer roleId : roleIds)
            roles.add(roleService.findRoleById(roleId));
        User user = new User();
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(enabled);
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/system/user";
    }

    @RequestMapping("/dash")
    public String dashboardPage(Model model) {
        return "dash";
    }
}
