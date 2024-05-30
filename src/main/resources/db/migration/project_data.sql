INSERT INTO category (name, is_active)
VALUES ('вебінар', TRUE),
       ('волонтерство', TRUE),
       ('грант', TRUE),
       ('конкурс', TRUE),
       ('конференція', TRUE),
       ('курс', TRUE),
       ('лекція', TRUE),
       ('майстер-клас', TRUE),
       ('хакатон', TRUE),
       ('обмін', TRUE),
       ('вакансія', TRUE),
       ('проєкт', TRUE),
       ('стажування', TRUE),
       ('стипендія', TRUE),
       ('табір', TRUE),
       ('турнір', TRUE),
       ('тренінг', TRUE);


INSERT INTO format (name, is_active)
VALUES ('онлайн', TRUE),
       ('офлайн', TRUE);

INSERT INTO user (email, password, role)
VALUES
       ('moderator@email.com', '12345678Aa', 'MODERATOR')
