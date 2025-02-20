<?php
ini_set("display_errors", 1);
error_reporting(E_ALL);
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);
/* schema
    CREATE TABLE `persona` (
      `id` int(11) NOT NULL,
      `nome` varchar(64) NOT NULL,
      `cognome` varchar(64) NOT NULL,
      `mail` varchar(255) DEFAULT NULL
    )

    CREATE TABLE `sede` (
      `id` int(11) NOT NULL,
      `nome` varchar(64) NOT NULL,
      `indirizzo` varchar(255) NOT NULL
    )

    CREATE TABLE `tessera` (
      `id` int(11) NOT NULL,
      `sede_creazione_id` int(11) NOT NULL,
      `punti` int(11) NOT NULL,
      `cliente_id` int(11) NOT NULL,
      `data_creazione` datetime NOT NULL DEFAULT current_timestamp()
    )
    */

/*
    WEB SERVICE MULTI-OPERAZIONE CON NOME DELL'OPERAZIONE IN URL
    richiesta http://localhost/scientology_market/smapi.php/operazione?querystring

    OPERAZIONI:
    -read (metodo: GET) (tipo: read) (parametro: content) (risposta: XML)
        -se content=clienti
            restituisce l'elenco dei clienti registrati e che quindi ad un certo punto hanno richiesto una tessera
        -se content=sedi
            restituisce l'elenco delle sedi
        -se content=tessere
            restituisce l'elenco di tutte le tessere
        -se content=popolarita_sedi
            restituisce l'elenco delle sedi con il numero totale di tessere mai create in tale sede ed il totale di tessere create per ogni mese se ce ne sono state
    */

/*

    */

// header per indicare al browser che la risposta sarà XML (e non HTML)
header("Content-Type: application/xml");

$statuscode = 405; //status code inizializzato a 405 Method Not Allowed

$uri_arr = parse_url($_SERVER["REQUEST_URI"]); //scompone l'uri in parti (vedi manuale)
$temp = explode("/", $uri_arr["path"]);
$op = end($temp); //estrae l'ultima parte dell'uri (dopo l'ultimo /) quindi l'ultima operazione richiesta

$conn = new mysqli("localhost", "root", "", "scientology_market");

if ($_SERVER["REQUEST_METHOD"] == "GET") {
    //switch per gestire le varie operazioni
    switch ($op) {
        case "read":
            //controllo se il parametro è adeguato per eseguire l'operazione
            if (isset($_GET["content"]) && !empty($_GET["content"])) {
                //switch per gestire la richiesta read in base al parametro content
                switch ($_GET["content"]) {
                    case "clienti":
                        $sql = "SELECT * FROM persona"; //query per estrarre tutti i clienti
                        $res = $conn->query($sql);
                        if ($res->num_rows == 0) {
                            $statuscode = 204; //l'operazione non ha estratto dati
                        } else {
                            $statuscode = 200; //l'operazione ha avuto successo
                        }

                        // elemento radice (attenzione: va scritto come se fosse un XML completo)
                        $xml = new SimpleXMLElement("<clienti_tesserati/>");
                        for ($i = 0; $i < $res->num_rows; $i++) {
                            $record = $res->fetch_assoc();

                            // aggiunta di un elemento figlio alla radice
                            $child = $xml->addChild("cliente");

                            // aggiunta di attributi ed elementi all'elemento figlio
                            $child->addAttribute("id", $record["id"]);
                            $child->addChild("nome", $record["nome"]);
                            $child->addChild("cognome", $record["cognome"]);
                            $child->addChild("mail", $record["mail"]);
                        }

                        $res->free();

                        // invio risposta XML al client
                        echo $xml->asXML();
                        break;
                    case "sedi":
                        $sql = "SELECT * FROM sede"; //query per estrarre tutte le sedi
                        $res = $conn->query($sql);
                        if ($res->num_rows == 0) {
                            $statuscode = 204; //l'operazione non ha estratto dati
                        } else {
                            $statuscode = 200; //l'operazione ha avuto successo
                        }

                        // elemento radice (attenzione: va scritto come se fosse un XML completo)
                        $xml = new SimpleXMLElement("<sedi/>");
                        for ($i = 0; $i < $res->num_rows; $i++) {
                            $record = $res->fetch_assoc();

                            // aggiunta di un elemento figlio alla radice
                            $child = $xml->addChild("sede");

                            // aggiunta di attributi ed elementi all'elemento figlio
                            $child->addChild("nome", $record["nome"]);
                            $child->addChild("indirizzo", $record["indirizzo"]);
                        }

                        $res->free();

                        // invio risposta XML al client
                        echo $xml->asXML();
                        break;
                    case "tessere":
                        //query per estrarre tutte le tessere create con i relativi dati della sede di creazione e del clientes
                        $sql =
                            "SELECT tessera.id, tessera.punti, tessera.data_creazione, CONCAT(sede.nome, ', ', sede.indirizzo) AS sede_di_creazione , persona.id AS cId, persona.nome AS cNome, persona.cognome AS cCognome, persona.mail AS cEmail FROM `tessera` JOIN sede ON sede.id=tessera.sede_creazione_id JOIN persona ON persona.id=tessera.cliente_id; ";
                        $res = $conn->query($sql);
                        if ($res->num_rows == 0) {
                            $statuscode = 204; //l'operazione non ha estratto dati
                        } else {
                            $statuscode = 200; //l'operazione ha avuto successo
                        }

                        // elemento radice (attenzione: va scritto come se fosse un XML completo)
                        $xml = new SimpleXMLElement("<tessere/>");
                        for ($i = 0; $i < $res->num_rows; $i++) {
                            $record = $res->fetch_assoc();

                            // aggiunta di un elemento figlio alla radice
                            $child = $xml->addChild("tessera");

                            // aggiunta di attributi ed elementi all'elemento figlio
                            $child->addAttribute(
                                "numero_tessera",
                                $record["id"]
                            );
                            $child->addChild("punti", $record["punti"]);
                            $child->addChild(
                                "data_di_creazione",
                                $record["data_creazione"]
                            );
                            $child->addChild(
                                "sede_di_creazione",
                                $record["sede_di_creazione"]
                            );
                            $clientechild = $child->addChild("cliente");
                            $clientechild->addAttribute("id", $record["cId"]);
                            $clientechild->addChild("nome", $record["cNome"]);
                            $clientechild->addChild(
                                "cognome",
                                $record["cCognome"]
                            );
                            $clientechild->addChild("mail", $record["cEmail"]);
                        }

                        $res->free();

                        // invio risposta XML al client
                        echo $xml->asXML();
                        break;
                    case "popolarita_sedi":
                        //query per estrarre il totale delle tessere create con la sede di creazione comprendendo anche le sedi che non ne hanno create
                        $sql =
                            "SELECT COUNT(tessera.id) AS 'n_tessere_create', sede.nome, sede.indirizzo, sede.id FROM sede LEFT JOIN tessera ON tessera.sede_creazione_id=sede.id GROUP BY sede.id ORDER BY tessera.sede_creazione_id; ";
                        $res = $conn->query($sql);
                        if ($res->num_rows == 0) {
                            $statuscode = 204; //l'operazione non ha estratto dati
                        } else {
                            $statuscode = 200; //l'operazione ha avuto successo
                        }

                        // elemento radice (attenzione: va scritto come se fosse un XML completo)
                        $xml = new SimpleXMLElement("<sedi/>");
                        for ($i = 0; $i < $res->num_rows; $i++) {
                            $record = $res->fetch_assoc();

                            // aggiunta di un elemento figlio alla radice
                            $child = $xml->addChild("sede");

                            // aggiunta di attributi ed elementi all'elemento figlio
                            $child->addAttribute("nome", $record["nome"]);
                            $child->addAttribute(
                                "indirizzo",
                                $record["indirizzo"]
                            );
                            $child->addAttribute(
                                "n_tot_di_tessere_create",
                                $record["n_tessere_create"]
                            );

                            //query per estrarre il totale delle tessere diviso per mese
                            $stmt = $conn->prepare(
                                "SELECT DATE_FORMAT(tessera.data_creazione ,'%M %Y') as mese, COUNT(tessera.id) AS ntessere FROM tessera JOIN sede ON sede.id=tessera.sede_creazione_id WHERE tessera.sede_creazione_id=? GROUP BY MONTH(tessera.data_creazione) ORDER BY tessera.sede_creazione_id ASC; "
                            );
                            $stmt->bind_param("i", $record["id"]);
                            $stmt->execute();
                            $childres = $stmt->get_result();

                            //aggiunta dei mesi in cui sono state create delle tessere con il relativo totale
                            for ($j = 0; $j < $childres->num_rows; $j++) {
                                $childrecord = $childres->fetch_assoc();
                                $sedechild = $child->addChild("periodo");
                                $sedechild->addAttribute(
                                    "mese",
                                    $childrecord["mese"]
                                );
                                $sedechild->addAttribute(
                                    "n_tessere_create",
                                    $childrecord["ntessere"]
                                );
                            }
                            $childres->free();
                        }

                        $res->free();

                        // invio risposta XML al client
                        echo $xml->asXML();
                        break;
                    default:
                        $statuscode = 400; //l'operazione ha restituito false per parametri invalidi
                        break;
                }
            } else {
                $statuscode = 400; //l'operazione ha restituito false per parametri insufficienti
            }
            break;

        default:
            $statuscode = 404; //operazione non presente nella web api
            break;
    }
} else {
    $statuscode = 404; //operazione non presente nella web api
}

http_response_code($statuscode); //invio lo status code della risposta

$conn->close();
?>
