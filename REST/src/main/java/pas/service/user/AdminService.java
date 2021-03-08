//package pas.service.user;
//
//import org.json.JSONObject;
//import pl.lodz.mm.pas.manager.UserManager;
//import pl.lodz.mm.pas.manager.exception.ManagerException;
//import pl.lodz.mm.pas.model.user.Admin;
//import pl.lodz.mm.pas.model.user.User;
//import pl.lodz.mm.pas.repository.exception.RepositoryException;
//import pl.lodz.mm.pas.service.dto.UserDto;
//import pl.lodz.mm.pas.service.exception.RestException;
//import pl.lodz.mm.pas.service.validation.user.ActivateUserValid;
//import pl.lodz.mm.pas.service.validation.user.AddUserValid;
//import pl.lodz.mm.pas.service.validation.user.UpdateUserValid;
//
//import javax.inject.Inject;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import java.lang.reflect.InvocationTargetException;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Produces({ MediaType.APPLICATION_JSON })
//@Consumes({ MediaType.APPLICATION_JSON })
//@Path("admin")
//public class AdminService {
//    @Inject
//    private UserManager userManager;
//
//    @POST
//    public String addAdmin(@AddUserValid UserDto userDto) {
//        Admin newUser = Admin.builder()
//                .firstname(userDto.getFirstname())
//                .lastname(userDto.getLastname())
//                .login(userDto.getLogin())
//                .password(userDto.getPassword())
//                .build();
//        try {
//            userManager.add(newUser);
//        } catch (RepositoryException | ManagerException e) {
//            throw new RestException(e.getMessage());
//        }
//        return JSONObject.wrap(userManager.get(newUser.getId())).toString();
//    }
//
//    @GET
//    public String getAllAdmins() {
//        return JSONObject.valueToString(userManager.getAll().stream().filter(x -> x instanceof Admin).collect(Collectors.toList()));
//    }
//
//    @Path("/{uuid}")
//    @GET
//    public String getAdmin(@PathParam("uuid") String id) {
//        User user;
//        try {
//            user = userManager.get(UUID.fromString(id));
//        } catch (IllegalArgumentException e) {
//            user = userManager.get(id);
//        }
//        return JSONObject.wrap(userManager.get(user.getId())).toString();
//    }
//
//    @PUT
//    public String updateAdmin(@UpdateUserValid UserDto userDto) {
//        Admin editingUser = Admin.builder()
//                .id(UUID.fromString(userDto.getId()))
//                .login(userDto.getLogin())
//                .password(userDto.getPassword())
//                .firstname(userDto.getFirstname())
//                .lastname(userDto.getLastname())
//                .build();
//        try {
//            userManager.update(editingUser);
//        } catch (ManagerException | RepositoryException | IllegalAccessException | InvocationTargetException e) {
//            throw new RestException(e.getMessage());
//        }
//        return JSONObject.wrap(userManager.get(UUID.fromString(userDto.getId()))).toString();
//    }
//
//    @PATCH
//    public String activateAdmin(@ActivateUserValid UserDto userDto) {
//        Admin user = (Admin) userManager.get(UUID.fromString(userDto.getId()));
//
//        Admin activatedUser = Admin.builder()
//                .id(UUID.fromString(userDto.getId()))
//                .firstname(user.getFirstname())
//                .lastname(user.getLastname())
//                .login(user.getLogin())
//                .password(user.getPassword())
//                .isActive(userDto.getIsActive())
//                .build();
//        try {
//            userManager.update(activatedUser);
//        } catch (RepositoryException | ManagerException | IllegalAccessException | InvocationTargetException e ) {
//            throw new RestException(e.getMessage());
//        }
//        return JSONObject.wrap(userManager.get(UUID.fromString(userDto.getId()))).toString();
//    }
//}
