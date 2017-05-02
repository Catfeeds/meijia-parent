
#处理users表积分和经验不一致的SQL
update users as T,
(select user_id, (addScore - subScore) as total, addExp from
(
	select user_id, 
	 (case when addScore is null then 0 else addScore end) as  addScore,
	 (case when subScore is null then 0 else subScore end) as  subScore,
	 (case when addExp is null then 0 else addExp end) as  addExp from
	(
	SELECT user_id, 
		   sum(case when is_consume = 0 then score end) as addScore, 
		   sum(case when is_consume = 1 then score end) as subScore,
		   sum(case when is_consume = 0 and action <> 'order' then score end) as addExp
	FROM `user_detail_score` group by user_id
	) as T1
) as T2
) as T3
set T.score = T3.total, T.exp = T3.addExp  where T.id = T3.user_id

ALTER TABLE `feeds` ADD `featured` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否精选 1 = 是 0 = 否' AFTER `status`;

ALTER TABLE `feeds` ADD FULLTEXT(`title`)  WITH PARSER ngram;


