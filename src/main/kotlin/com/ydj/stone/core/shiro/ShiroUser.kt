package com.ydj.stone.core.shiro

import java.io.Serializable

class ShiroUser( /**
                  * 主键ID
                  */
                  var id: Int ? = null,
                /**
                 * 账号
                 */
                 var account :String? = null,
                /**
                 * 姓名
                 */
                 var name : String ? = null,
                /**
                 * 部门id
                 */
                 var deptId: Int ? = null,
                /**
                 * 角色集
                 */
                 var roleList: List<Int>? = null,
                /**
                 * 部门名称
                 */
                 var deptName : String ? =null,
                /**
                 * 角色名称集
                 */
                 var roleNames: List<String> ? =null
): Serializable