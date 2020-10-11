import { createWrapper } from "next-redux-wrapper";
import { createStore } from "redux";
import reducers from "./reducers";

const store = createStore(reducers);

const initStore = () => {
  return store;
};

const makeStore = (context) => store;

//export const wrapper = createWrapper(initStore);
export const wrapper = createWrapper(makeStore, { debug: false });

export default store;
