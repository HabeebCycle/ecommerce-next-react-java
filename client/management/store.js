import { createWrapper } from "next-redux-wrapper";
import { createStore } from "redux";
import reducers from "./reducers";

const store = createStore(reducers);

const initStore = () => {
  return store;
};

export const wrapper = createWrapper(initStore);

export default store;
