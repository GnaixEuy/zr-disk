package cn.realandy.zrdisk.controller.admin;

import cn.realandy.zrdisk.dao.relation.UserRoleAssociatedDao;
import cn.realandy.zrdisk.entity.relation.UserRoleAssociated;
import cn.realandy.zrdisk.vo.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.Map;

@RestController
@RequestMapping(value = {"/admin/role"})
public class AdminRoleController {

    private UserRoleAssociatedDao userRoleAssociatedDao;

    //修改权限
    @PutMapping("/updateRole")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseResult<String> updateRole(@RequestBody Map<String, Object> map) {
        Integer id = (Integer) map.get("id");
        QueryWrapper<UserRoleAssociated> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        queryWrapper.eq("role_id", 2);
        UserRoleAssociated userRoleAssociated = new UserRoleAssociated(2, id);
        if (this.userRoleAssociatedDao.selectList(queryWrapper).size() == 1) {
            this.userRoleAssociatedDao.delete(queryWrapper);
        } else {
            this.userRoleAssociatedDao.insert(userRoleAssociated);
        }
        return ResponseResult.success("权限修改成功");
    }

    @Autowired
    public void setUserRoleAssociatedDao(UserRoleAssociatedDao userRoleAssociatedDao) {
        this.userRoleAssociatedDao = userRoleAssociatedDao;
    }
}
