describe("The Login Page", () => {
  it("Login and logout trainer", function() {
    // destructuring assignment of the this.currentUser object
    let username = Cypress.env("trainer.username");
    let password = Cypress.env("trainer.password");

    cy.visit("/auth/login");

    cy.get("input[data-cy=username]").type(username);

    // {enter} causes the form to submit
    cy.get("input[data-cy=password]").type(`${password}{enter}`);

    // we should be redirected to /dashboard
    cy.location("pathname").should("equal", "/trainer/dashboard");

    // UI should reflect this user being logged in
    cy.get("button[data-cy=userMenu]").click();
    cy.get("div[data-cy=userFullName]")
      .should("contain", username)
      .then(() => {
        // token should exist
        const token = window.localStorage.getItem("token");
        expect(token).to.be.a("string");
      });

    // LOGOUT
    cy.get("div[data-cy=logout]").click();
    cy.location("pathname").should("equal", "/auth/login");
  });

  it("Does not log in with invalid password", () => {
    cy.visit("/");
    cy.location("pathname").should("equal", "/auth/login");

    // enter valid username and password
    cy.get("input[data-cy=username]").type("username");
    cy.get("input[data-cy=password]").type("wrong-password");
    cy.get("button[data-cy=login]").click();

    // still on /login page plus an error is displayed
    cy.location("pathname").should("equal", "/auth/login");
    cy.get(".v-alert__content").should("be.visible");
  });
});
