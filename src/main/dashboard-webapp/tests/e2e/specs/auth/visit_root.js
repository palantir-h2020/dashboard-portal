// https://docs.cypress.io/api/introduction/api.html

describe("Load /", () => {
  it("Visits the app root url and refresh", () => {
    cy.visit("/");
    cy.get("main").should("exist");
    cy.reload();
    cy.get("main").should("exist");
    cy.reload(true);
    cy.get("main").should("exist");
  });
});
