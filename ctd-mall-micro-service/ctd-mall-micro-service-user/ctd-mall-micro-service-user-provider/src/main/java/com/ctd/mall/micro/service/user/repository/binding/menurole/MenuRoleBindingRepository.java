package com.ctd.mall.micro.service.user.repository.binding.menurole;

import com.ctd.mall.micro.service.user.domain.binding.menurole.MenuRoleBinding;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MenuRoleBindingRepository
 *
 * @author chentudong
 * @date 2020/3/13 10:12 上午
 * @since 1.0
 */
public interface MenuRoleBindingRepository extends JpaRepository<MenuRoleBinding, String>
{
}
