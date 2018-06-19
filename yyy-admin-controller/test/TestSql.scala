object TestSql extends App {
  println(s"use skill_store;")
  (0 to 99).foreach { i =>
    println(s"alter table user_skill_$i add column `enabled_privacy_list` varchar(512) default '[]' COMMENT '用户允许的技能数据隐私';")
  }
}
