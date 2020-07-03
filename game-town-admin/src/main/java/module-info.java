module game.town.game.town.admin.main {
    requires static spring.web;
    requires static spring.context;
    requires static spring.boot.autoconfigure;
    requires static spring.boot;
    requires static handlebars.spring.boot.starter;
    requires static handlebars;

    requires game.town.gt.account.core.main;
}