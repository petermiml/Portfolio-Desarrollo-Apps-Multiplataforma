using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class OrbController : MonoBehaviour
{
    // Variable que almacena la puntuación que se obtiene cuando se coge un orbe.
    private int pointsValue = 5;

    // Script que tendrá un objeto externo controlador de puntos.
    private PointsController pointsController;
    
    // Script del player
    private PlayerController playerScript;

    void Start()
    {
        playerScript = FindObjectOfType<PlayerController>();
        pointsController = FindObjectOfType<PointsController>();
        
    }

    void Update()
    {
 
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        // Si el player entra en el trigger del orbe:
        if(collision.tag == "Player")
        {
            playerScript.OrbSound();                        // Ejecuta el sonido del orbe desde el script del player
            pointsController.SafePoints(pointsValue);       // Guarda los puntos en el script del objeto controlador de puntos.
            Destroy(gameObject);                            // Destruye el orbe.
        }
    }

}
