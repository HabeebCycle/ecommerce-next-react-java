import Head from "next/head";
import { useState } from "react";
import styles from "../styles/Home.module.css";
import Layout from "../components/layouts/Layout";
import store from "../management/store";
import { setUserLogged } from "../management/reducers/authReducer/actions";

export default function Home() {
  //store.dispatch(setFetchError("err.message"));

  const [login, setLogin] = useState(false);

  const handleLogin = () => {
    const setLog = !login;
    setLogin(setLog);
    store.dispatch(setUserLogged(setLog));
  };

  return (
    <Layout>
      <div className={styles.container}>
        <Head>
          <title>Create Next App</title>
        </Head>
        <main className={styles.main}>
          <p>Almost before we knew it, we had left the building.</p>
          <p>{login ? "I'm Logged In" : "I'm logged out"}</p>
          <button onClick={handleLogin}>{login ? "Log out" : "Log In"}</button>
        </main>

        <footer className={styles.footer}></footer>
      </div>
    </Layout>
  );
}
