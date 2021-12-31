describe("The Sign Up Page", () => {
  it("Sign up trainee", () => {
    cy.visit("/auth/sign-up");
    cy.get("input[data-cy=username]").type("test");
    cy.get("input[data-cy=email]").type("test@example.com");
    cy.get("input[data-cy=password]").type("test");
    cy.get("input[data-cy=confirmPassword]").type("test");
    cy.get("button[data-cy=register]").click();
    cy.contains(/You have been registered successfully|Username already exists/g);
  });
});
