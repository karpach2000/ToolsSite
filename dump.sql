--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.21
-- Dumped by pg_dump version 9.5.21

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE ONLY public.roles_to_users DROP CONSTRAINT roles_to_users_users_id_fkey;
ALTER TABLE ONLY public.roles_to_users DROP CONSTRAINT roles_to_users_roles_id_fkey;
ALTER TABLE ONLY public.messages_u_2_u DROP CONSTRAINT messages_u_2_u_users_id_to_fkey;
ALTER TABLE ONLY public.messages_u_2_u DROP CONSTRAINT messages_u_2_u_users_id_from_fkey;
ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
ALTER TABLE ONLY public.roles_to_users DROP CONSTRAINT roles_to_users_pkey;
ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_pkey;
ALTER TABLE ONLY public.messages_u_2_u DROP CONSTRAINT messages_u_2_u_pkey;
ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.roles_to_users ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.roles ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.messages_u_2_u ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE public.users_id_seq;
DROP TABLE public.users;
DROP SEQUENCE public.user_ids;
DROP SEQUENCE public.roles_to_users_id_seq;
DROP TABLE public.roles_to_users;
DROP SEQUENCE public.roles_id_seq;
DROP TABLE public.roles;
DROP SEQUENCE public.messages_u_2_u_id_seq;
DROP TABLE public.messages_u_2_u;
DROP FUNCTION public.get_users_whith_roles();
DROP FUNCTION public.get_users_whith_role(_role character varying);
DROP EXTENSION plpgsql;
DROP SCHEMA users_manager;
DROP SCHEMA public;
--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- Name: users_manager; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA users_manager;


ALTER SCHEMA users_manager OWNER TO postgres;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: get_users_whith_role(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.get_users_whith_role(_role character varying) RETURNS SETOF text
    LANGUAGE plpgsql
    AS $$
DECLARE
	r varchar(50);
BEGIN
	FOR r IN
		SELECT users.login, roles.user_role FROM roles_to_users 
		JOIN users ON users.id = roles_to_users.users_id
		JOIN roles ON roles.id = roles_to_users.roles_id
		WHERE roles.user_role = _role 
	LOOP 
		RETURN NEXT r; 
	END LOOP;
	RETURN;
END
$$;


ALTER FUNCTION public.get_users_whith_role(_role character varying) OWNER TO postgres;

--
-- Name: get_users_whith_roles(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.get_users_whith_roles() RETURNS TABLE(user_login character varying, user_role character varying)
    LANGUAGE plpgsql
    AS $$
DECLARE
 _users varchar(50);
 _roles varchar(50);
BEGIN
		FOR user_login, user_role IN
			SELECT users.login, roles.user_role FROM roles_to_users 
			JOIN users ON users.id = roles_to_users.users_id
			JOIN roles ON roles.id = roles_to_users.roles_id
		LOOP
			RETURN NEXT;
		END LOOP;
		RETURN;
END
$$;


ALTER FUNCTION public.get_users_whith_roles() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: messages_u_2_u; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.messages_u_2_u (
    id integer NOT NULL,
    users_id_from integer,
    users_id_to integer,
    message text
);


ALTER TABLE public.messages_u_2_u OWNER TO postgres;

--
-- Name: messages_u_2_u_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.messages_u_2_u_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.messages_u_2_u_id_seq OWNER TO postgres;

--
-- Name: messages_u_2_u_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.messages_u_2_u_id_seq OWNED BY public.messages_u_2_u.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    user_role character(64),
    description character(64)
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_id_seq OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- Name: roles_to_users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles_to_users (
    id integer NOT NULL,
    users_id integer,
    roles_id integer
);


ALTER TABLE public.roles_to_users OWNER TO postgres;

--
-- Name: roles_to_users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_to_users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_to_users_id_seq OWNER TO postgres;

--
-- Name: roles_to_users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_to_users_id_seq OWNED BY public.roles_to_users.id;


--
-- Name: user_ids; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_ids
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_ids OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    login character(64),
    password character(64)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages_u_2_u ALTER COLUMN id SET DEFAULT nextval('public.messages_u_2_u_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_to_users ALTER COLUMN id SET DEFAULT nextval('public.roles_to_users_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: messages_u_2_u; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.messages_u_2_u (id, users_id_from, users_id_to, message) FROM stdin;
1	1	1	Hay
\.


--
-- Name: messages_u_2_u_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.messages_u_2_u_id_seq', 1, true);


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, user_role, description) FROM stdin;
1	User                                                            	Обычный пользователь                                            
2	Admin                                                           	Администратор                                                   
\.


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 2, true);


--
-- Data for Name: roles_to_users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles_to_users (id, users_id, roles_id) FROM stdin;
2	3	1
5	4	1
6	2	1
7	2	2
8	1	2
\.


--
-- Name: roles_to_users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_to_users_id_seq', 8, true);


--
-- Name: user_ids; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_ids', 1, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, login, password) FROM stdin;
1	afiskon                                                         	123456                                                          
2	karpach2000                                                     	256                                                             
3	di.romanov                                                      	Profit18                                                        
4	kola                                                            	kola                                                            
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 4, true);


--
-- Name: messages_u_2_u_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages_u_2_u
    ADD CONSTRAINT messages_u_2_u_pkey PRIMARY KEY (id);


--
-- Name: roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: roles_to_users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_to_users
    ADD CONSTRAINT roles_to_users_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: messages_u_2_u_users_id_from_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages_u_2_u
    ADD CONSTRAINT messages_u_2_u_users_id_from_fkey FOREIGN KEY (users_id_from) REFERENCES public.users(id);


--
-- Name: messages_u_2_u_users_id_to_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages_u_2_u
    ADD CONSTRAINT messages_u_2_u_users_id_to_fkey FOREIGN KEY (users_id_to) REFERENCES public.roles(id);


--
-- Name: roles_to_users_roles_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_to_users
    ADD CONSTRAINT roles_to_users_roles_id_fkey FOREIGN KEY (roles_id) REFERENCES public.roles(id);


--
-- Name: roles_to_users_users_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles_to_users
    ADD CONSTRAINT roles_to_users_users_id_fkey FOREIGN KEY (users_id) REFERENCES public.users(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;
GRANT USAGE ON SCHEMA public TO karpach2000;


--
-- Name: FUNCTION get_users_whith_role(_role character varying); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION public.get_users_whith_role(_role character varying) FROM PUBLIC;
REVOKE ALL ON FUNCTION public.get_users_whith_role(_role character varying) FROM postgres;
GRANT ALL ON FUNCTION public.get_users_whith_role(_role character varying) TO postgres;
GRANT ALL ON FUNCTION public.get_users_whith_role(_role character varying) TO PUBLIC;
GRANT ALL ON FUNCTION public.get_users_whith_role(_role character varying) TO karpach2000;


--
-- Name: FUNCTION get_users_whith_roles(); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION public.get_users_whith_roles() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.get_users_whith_roles() FROM postgres;
GRANT ALL ON FUNCTION public.get_users_whith_roles() TO postgres;
GRANT ALL ON FUNCTION public.get_users_whith_roles() TO PUBLIC;
GRANT ALL ON FUNCTION public.get_users_whith_roles() TO karpach2000;


--
-- Name: TABLE messages_u_2_u; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.messages_u_2_u FROM PUBLIC;
REVOKE ALL ON TABLE public.messages_u_2_u FROM postgres;
GRANT ALL ON TABLE public.messages_u_2_u TO postgres;
GRANT ALL ON TABLE public.messages_u_2_u TO karpach2000;


--
-- Name: SEQUENCE messages_u_2_u_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE public.messages_u_2_u_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE public.messages_u_2_u_id_seq FROM postgres;
GRANT ALL ON SEQUENCE public.messages_u_2_u_id_seq TO postgres;
GRANT ALL ON SEQUENCE public.messages_u_2_u_id_seq TO karpach2000;


--
-- Name: TABLE roles; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.roles FROM PUBLIC;
REVOKE ALL ON TABLE public.roles FROM postgres;
GRANT ALL ON TABLE public.roles TO postgres;
GRANT ALL ON TABLE public.roles TO karpach2000;


--
-- Name: SEQUENCE roles_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE public.roles_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE public.roles_id_seq FROM postgres;
GRANT ALL ON SEQUENCE public.roles_id_seq TO postgres;
GRANT ALL ON SEQUENCE public.roles_id_seq TO karpach2000;


--
-- Name: TABLE roles_to_users; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.roles_to_users FROM PUBLIC;
REVOKE ALL ON TABLE public.roles_to_users FROM postgres;
GRANT ALL ON TABLE public.roles_to_users TO postgres;
GRANT ALL ON TABLE public.roles_to_users TO karpach2000;


--
-- Name: SEQUENCE roles_to_users_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE public.roles_to_users_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE public.roles_to_users_id_seq FROM postgres;
GRANT ALL ON SEQUENCE public.roles_to_users_id_seq TO postgres;
GRANT ALL ON SEQUENCE public.roles_to_users_id_seq TO karpach2000;


--
-- Name: SEQUENCE user_ids; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE public.user_ids FROM PUBLIC;
REVOKE ALL ON SEQUENCE public.user_ids FROM postgres;
GRANT ALL ON SEQUENCE public.user_ids TO postgres;
GRANT ALL ON SEQUENCE public.user_ids TO karpach2000;


--
-- Name: TABLE users; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.users FROM PUBLIC;
REVOKE ALL ON TABLE public.users FROM postgres;
GRANT ALL ON TABLE public.users TO postgres;
GRANT ALL ON TABLE public.users TO karpach2000;


--
-- Name: SEQUENCE users_id_seq; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON SEQUENCE public.users_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE public.users_id_seq FROM postgres;
GRANT ALL ON SEQUENCE public.users_id_seq TO postgres;
GRANT ALL ON SEQUENCE public.users_id_seq TO karpach2000;


--
-- PostgreSQL database dump complete
--

