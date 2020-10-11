import { useEffect, useState } from "react";
import { Provider } from "react-redux";
import store, { wrapper } from "../management/store";
import "../styles/globals.css";
import "../styles/style.scss";

const MyApp = ({ Component, pageProps }) => {
  const [open, setOpen] = useState(false);

  useEffect(() => {
    setTimeout(() => {
      document.getElementById("__next").classList.add("loaded");
    }, 100);
    setOpen(true);
  }, []);

  console.log("Page Open", open);
  return (
    <Provider store={store}>
      <Component {...pageProps} />
    </Provider>
  );
};

//const makeStore = () => store;
store.subscribe(() => fancyLog());

function fancyLog() {
  console.log("%c Rendered with ? ??", "background: purple; color: #fff");
  console.log(store.getState());
}

export default wrapper.withRedux(MyApp);
