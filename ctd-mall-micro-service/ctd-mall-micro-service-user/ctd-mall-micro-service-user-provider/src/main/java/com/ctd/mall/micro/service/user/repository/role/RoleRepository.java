package com.ctd.mall.micro.service.user.repository.role;

import com.ctd.mall.micro.service.user.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RoleRepository
 *
 * @author chentudong
 * @date 2020/3/14 16:20
 * @since 1.0
 */
public interface RoleRepository extends JpaRepository<Role, String>
{
    /**
     * findByCodeAndName
     *
     * @param code code
     * @param name name
     * @return Role
     */
    Role findByCodeAndName(String code, String name);

    /**
     * findByName
     *
     * @param name name
     * @return Role
     */
    Role findByName(String name);

    /**
     * findByCode
     *
     * @param code code
     * @return Role
     */
    Role findByCode(String code);
}
