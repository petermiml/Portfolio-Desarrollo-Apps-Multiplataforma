using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CollectibleHeartController : MonoBehaviour
{
    // Variable que almacena el valor en puntos del corazón (siempre que el jugador lo coja teniendo todas las vidas).
    private int pointsValue = 10;

    // Scripts necesarios
    private PlayerController playerController;
    private PointsController pointsController;
    
    // Start is called before the first frame update
    void Start()
    {
        playerController = FindObjectOfType<PlayerController>();
        pointsController = FindObjectOfType<PointsController>();
    }

    // Update is called once per frame
    void Update()
    {
        // Rotamos el corazón sobre su eje y.
        transform.Rotate(Vector3.up);
    }

    public void OnTriggerEnter2D(Collider2D collision)
    {
        // Si el jugador atraviesa el trigger del corazón y le falta alguna vida (siempre que no tenga 0 porque estaría muerto) gana una vida.
        // En caso contrario, aumentará en 10 sus puntos y guardandolos en el pointsController.
        if(collision.gameObject.tag == "Player")
        {
            if (playerController.totalLifes < 3 && playerController.totalLifes > 0)
            {
                playerController.totalLifes += 1;
            }
            else
            {
                pointsController.SafePoints(pointsValue);
            }
            // Ejecutamos el sonido que se realiza al coger el corazón y destruimos el objeto.
            playerController.HeartSound();
            Destroy(gameObject);

        }
    }
}
