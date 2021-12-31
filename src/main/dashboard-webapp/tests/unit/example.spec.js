import { shallowMount } from "@vue/test-utils";
import Vue from "vue";
import Vuetify from "vuetify";
import Header from "@/components/Header";

Vue.use(Vuetify);

describe("TopMenu.vue", () => {
  let vuetify;

  beforeEach(() => {
    vuetify = new Vuetify();
  });

  it("renders component", () => {
    const wrapper = shallowMount(Header, {
      vuetify
    });
    expect(wrapper.html()).toContain("");
  });
});
