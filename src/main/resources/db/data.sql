INSERT INTO user_tb(username, password, email, profile, created_at) values('ssar', '1234', 'ssar@nate.com','/images/dm.png', now());
INSERT INTO user_tb(username, password, email, profile, created_at) values('love', '1234', 'love@nate.com','/images/dm.png', now());


INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('1번째 제목', '1번째 내용','/images/dm.png', 1, now());
INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('2번째 제목', '2번째 내용','/images/dm.png', 1, now());
INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('3번째 제목', '3번째 내용','/images/dm.png', 1, now());
INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('4번째 제목', '4번째 내용','/images/dm.png', 2, now());
INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('5번째 제목', '5번째 내용','/images/dm.png', 2, now());
INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('6번째 제목', '6번째 내용','/images/dm.png', 2, now());

insert into reply_tb(comment, board_id, user_id, created_at) values('댓글1', 1, 1, now());
insert into reply_tb(comment, board_id, user_id, created_at) values('댓글1', 1, 2, now());
insert into reply_tb(comment, board_id, user_id, created_at) values('댓글1', 2, 1, now());
insert into reply_tb(comment, board_id, user_id, created_at) values('댓글1', 2, 2, now());
insert into reply_tb(comment, board_id, user_id, created_at) values('댓글1', 1, 3, now());
insert into reply_tb(comment, board_id, user_id, created_at) values('댓글1', 2, 3, now());

-- insert into love_tb(board_id,user_id,created_at) values(1,1,now());
-- insert into love_tb(board_id,user_id,created_at) values(1,2,now());

commit;