--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


--
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: diseases; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.diseases (
    disease character varying(255) NOT NULL,
    district character varying(255) NOT NULL,
    year smallint NOT NULL,
    abs integer,
    abs_child integer,
    predicted boolean,
    rel integer,
    rel_child integer
);


ALTER TABLE public.diseases OWNER TO postgres;

--
-- Name: federal_districts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.federal_districts (
    district character varying(255) NOT NULL
);


ALTER TABLE public.federal_districts OWNER TO postgres;

--
-- Data for Name: diseases; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.diseases (disease, district, year, abs, abs_child, predicted, rel, rel_child) FROM stdin;
\.


--
-- Data for Name: federal_districts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.federal_districts (district) FROM stdin;
\.


--
-- Name: diseases diseases_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.diseases
    ADD CONSTRAINT diseases_pkey PRIMARY KEY (disease, district, year);


--
-- Name: federal_districts federal_districts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.federal_districts
    ADD CONSTRAINT federal_districts_pkey PRIMARY KEY (district);


--
-- PostgreSQL database dump complete
--

