using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SoulFragmentController : MonoBehaviour
{

    
    private float speed = 0.8f;                 // Velocidad a la que oscila arriba y abajo.
    public Transform target;                    // Posición bjetivo que alternará con la original del objeto para que el SoulFragment de la sensación que flota.
    private Vector3 start, end;                 // Variables que nos ayudarán a alternar la posición del target con la inicial del objeto.
    private PlayerController playerController;  // Script del player

    // Start is called before the first frame update
    void Start()
    {
        start = transform.position;
        end = target.position;
        playerController = FindObjectOfType<PlayerController>();
    }

    // Update is called once per frame
    void Update()
    {
       // MOVIMIENTO DEL OBJETO
        transform.position = Vector3.MoveTowards(transform.position, target.position, speed * Time.deltaTime);
       
        if (transform.position == target.position)
        {
            if (target.position == end)
            {
                target.position = start;
            }
            else
            {
                target.position = end;
            }
        }
    }

    public void OnTriggerEnter2D(Collider2D collision)
    {
        // Si el personaje colisiona con este objeto, se activa el sonido del alma y se indica al script del player que tiene el alma.
        // Destruye el objeto.
        if(collision.tag == "Player")
        {
            playerController.SoulSound();
            playerController.hasSoulFragment = true;
            Destroy(gameObject);
        }
    }
}
