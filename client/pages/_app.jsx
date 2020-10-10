import { useEffect, useState } from "react";
import store, { wrapper } from "../management/store";
import "../styles/globals.css";
import "../styles/style.scss";

function MyApp({ Component, pageProps }) {
  const [open, setOpen] = useState(false);

  useEffect(() => {
    setTimeout(() => {
      document.getElementById("__next").classList.add("loaded");
    }, 100);
    setOpen(true);
  }, []);

  fancyLog();
  console.log("Page Open", open);
  return <Component {...pageProps} />;
}

//const makeStore = () => store;

function fancyLog() {
  console.log("%c Rendered with ? ??", "background: purple; color: #fff");
  console.log(store.getState());
}

export default wrapper.withRedux(MyApp);
