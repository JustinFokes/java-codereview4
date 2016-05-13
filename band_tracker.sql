--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.2
-- Dumped by pg_dump version 9.5.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: bands; Type: TABLE; Schema: public; Owner: justinfokes
--

CREATE TABLE bands (
    id integer NOT NULL,
    name character varying,
    genre character varying,
    hometown character varying
);


ALTER TABLE bands OWNER TO justinfokes;

--
-- Name: bands_id_seq; Type: SEQUENCE; Schema: public; Owner: justinfokes
--

CREATE SEQUENCE bands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bands_id_seq OWNER TO justinfokes;

--
-- Name: bands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: justinfokes
--

ALTER SEQUENCE bands_id_seq OWNED BY bands.id;


--
-- Name: bands_venues; Type: TABLE; Schema: public; Owner: justinfokes
--

CREATE TABLE bands_venues (
    id integer NOT NULL,
    band_id integer,
    venue_id integer
);


ALTER TABLE bands_venues OWNER TO justinfokes;

--
-- Name: bands_venues_id_seq; Type: SEQUENCE; Schema: public; Owner: justinfokes
--

CREATE SEQUENCE bands_venues_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bands_venues_id_seq OWNER TO justinfokes;

--
-- Name: bands_venues_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: justinfokes
--

ALTER SEQUENCE bands_venues_id_seq OWNED BY bands_venues.id;


--
-- Name: venues; Type: TABLE; Schema: public; Owner: justinfokes
--

CREATE TABLE venues (
    id integer NOT NULL,
    name character varying,
    phone character varying,
    location character varying
);


ALTER TABLE venues OWNER TO justinfokes;

--
-- Name: venues_id_seq; Type: SEQUENCE; Schema: public; Owner: justinfokes
--

CREATE SEQUENCE venues_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE venues_id_seq OWNER TO justinfokes;

--
-- Name: venues_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: justinfokes
--

ALTER SEQUENCE venues_id_seq OWNED BY venues.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: justinfokes
--

ALTER TABLE ONLY bands ALTER COLUMN id SET DEFAULT nextval('bands_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: justinfokes
--

ALTER TABLE ONLY bands_venues ALTER COLUMN id SET DEFAULT nextval('bands_venues_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: justinfokes
--

ALTER TABLE ONLY venues ALTER COLUMN id SET DEFAULT nextval('venues_id_seq'::regclass);


--
-- Data for Name: bands; Type: TABLE DATA; Schema: public; Owner: justinfokes
--

COPY bands (id, name, genre, hometown) FROM stdin;
52	The Wiggles	Pop	Tacoma
\.


--
-- Name: bands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: justinfokes
--

SELECT pg_catalog.setval('bands_id_seq', 52, true);


--
-- Data for Name: bands_venues; Type: TABLE DATA; Schema: public; Owner: justinfokes
--

COPY bands_venues (id, band_id, venue_id) FROM stdin;
57	52	31
58	52	31
\.


--
-- Name: bands_venues_id_seq; Type: SEQUENCE SET; Schema: public; Owner: justinfokes
--

SELECT pg_catalog.setval('bands_venues_id_seq', 58, true);


--
-- Data for Name: venues; Type: TABLE DATA; Schema: public; Owner: justinfokes
--

COPY venues (id, name, phone, location) FROM stdin;
31	NamHotel	Unknown	Unkown
\.


--
-- Name: venues_id_seq; Type: SEQUENCE SET; Schema: public; Owner: justinfokes
--

SELECT pg_catalog.setval('venues_id_seq', 31, true);


--
-- Name: bands_pkey; Type: CONSTRAINT; Schema: public; Owner: justinfokes
--

ALTER TABLE ONLY bands
    ADD CONSTRAINT bands_pkey PRIMARY KEY (id);


--
-- Name: bands_venues_pkey; Type: CONSTRAINT; Schema: public; Owner: justinfokes
--

ALTER TABLE ONLY bands_venues
    ADD CONSTRAINT bands_venues_pkey PRIMARY KEY (id);


--
-- Name: venues_pkey; Type: CONSTRAINT; Schema: public; Owner: justinfokes
--

ALTER TABLE ONLY venues
    ADD CONSTRAINT venues_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: justinfokes
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM justinfokes;
GRANT ALL ON SCHEMA public TO justinfokes;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

