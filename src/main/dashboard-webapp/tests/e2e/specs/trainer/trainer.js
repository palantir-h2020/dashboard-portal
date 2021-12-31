// login just once using API

describe("Trainer", () => {
  let res_data;
  let jwt;

  before(function fetchUser() {
    let credentials = {};
    credentials.username = Cypress.env("trainer.username");
    credentials.password = Cypress.env("trainer.password");
    credentials.grant_type = "password";
    let authUrl = Cypress.env("apiBase") + "/api/v1/auth/login";
    cy.request("POST", authUrl, credentials)
      .its("body")
      .then(res => {
        res_data = res;
        cy.log(res_data.access_token);
        cy.parseJwt(res_data.access_token).then(value => {
          jwt = value;
        });
      });
  });

  // but set the user before visiting the page
  // so the app thinks it is already authenticated
  beforeEach(function setUser() {
    cy.visit("/", {
      onBeforeLoad(win) {
        // and before the page finishes loading
        // set the user object in local storage
        win.localStorage.setItem("userfullname", jwt.userfullname);
        win.localStorage.setItem("userid", jwt.userid);
        win.localStorage.setItem("roles", jwt.realm_access.roles);
        if (jwt.avatar) {
          win.localStorage.setItem("avatar", jwt.avatar);
        }
        win.localStorage.setItem("token", res_data.access_token);
        win.localStorage.setItem("refresh_token", res_data.refresh_token);
        win.localStorage.setItem("expires_in", res_data.expires_in);
        let expires_in_timestamp = Date.now() + res_data.expires_in * 1000;
        win.localStorage.setItem("expires_in_timestamp", expires_in_timestamp);
        win.localStorage.setItem("tokenIsRefreshing", false);
      }
    });
    // the page should be opened and the user should be logged in
  });

  it("Visit dashboard", () => {
    cy.visit("/dashboard");
  });

  it("Persons view list, pagination, sorting, filtering", () => {
    cy.visit("/dashboard");
    cy.get("div[data-cy=Persons]").click();
    // list
    cy.get("table")
      .find("tr")
      .should("have.length.of.at.least", 5);
    // pagination
    cy.get('button[aria-label="Next page"]').click();
    cy.get("div[class=v-data-footer__pagination").contains("11-");
    cy.url().should("include", "index=1");
    cy.get('button[aria-label="Previous page"]').click();
    // sorting
    cy.get("table")
      .contains("th", "First Name")
      .click();
    cy.url().should("include", "sort=firstName%20asc");
    // filtering
    cy.get("div[data-cy=gender]").click();
    cy.get("div[data-cy=gender]")
      .get('div[class="v-list-item__title"')
      .contains("Female")
      .click();
    cy.get("button[data-cy=filter]").click();
    cy.url().should("include", "gender=FEMALE");
    cy.get("table").contains("td", "Female");
    cy.get("table").should("not.contain", "Male");
  });

  it("Persons view, add, edit, delete (non modal)", () => {
    cy.visit("/dashboard");
    cy.get("div[data-cy=Persons]").click();
    cy.get("a[data-cy=add]").click();
    cy.get("input[data-cy=lastName]").type("Test last name");
    cy.get("input[data-cy=firstName]").type("Test first name");
    cy.get("input[data-cy=birthDate]").type("07/05/1993");
    cy.get("input[data-cy=gender]").click();
    cy.get("input[data-cy=gender]")
      .get('div[class="v-list-item__title"')
      .contains("Female")
      .click();
    cy.get("button[data-cy=submit]").click();
    cy.contains("has been created");
    // TODO add edit delete view
  });

  it("Crime Reports view, add, edit, delete (non modal)", () => {
    cy.visit("/dashboard");
    cy.get("div[data-cy='Crime Reports']").click();
    cy.get("a[data-cy=add]").click();
    cy.get("input[data-cy=status]").click();
    cy.get("input[data-cy=status]")
      .get('div[class="v-list-item__title"')
      .contains("Unsolved")
      .click();
    cy.get("input[data-cy=receivers]").click();
    cy.get("input[data-cy=receivers]")
      .get('div[class="v-list-item__title"')
      .contains("Ubitech")
      .click();
    cy.get("textarea[data-cy=description]").type("This is a test description");
    cy.get("button[data-cy=submit]").click();
    cy.contains("has been created");
    // TODO add edit delete view
  });
});
