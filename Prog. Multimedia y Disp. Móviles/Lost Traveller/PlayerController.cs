using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour
{
    public float speed = 8f;             // Velocidad del player          
    private float jumpForce = 15f;          // Fuerza del salto
    private float pushForce = 10f;          // Fuerza con la que el enemigo te empuja cuando te hiere.
    private int jumpCont = 0;               // Contador de saltos para evitar que el jugador salte más de 2 veces.
    public int totalLifes = 3;              // Contador de vidas.
    private bool canMove = true;            // True: El personaje podrá moverse.
    public bool hasSoulFragment = false;    // True: El jugador tiene el fragmento de alma (llave para ganar).
    public bool isFinish = false;           // True: El jugador ha llegado al final con el fragmento del alma.
    private float entradaX;

    // Componentes necesarios
    private Animator playerAnimator;            
    private SpriteRenderer flipPlayer;
    private Rigidbody2D playerRb;
    private AudioSource audioSourcePlayer;
    public AudioClip jump, orbCollectSound, heartSound, soulSound;

    // GameObjects necesarios
    public GameObject cameraPlayer;
    public GameObject panelGameOver, panelWin;
    public GameObject playerExplosionPrefab, enemyExplosionPrefab;
    public GameObject needToWinText;
    public GameObject spawnBombs;

    // Scripts necesarios
    private OctopusController octopusScript;
    private CameraSoundController cameraScript;
    private PointsController pointsController;
    // Método que se ejecuta en el primer frame del juego.
    void Start()
    {
        flipPlayer = GetComponent<SpriteRenderer>();
        playerRb = GetComponent<Rigidbody2D>();
        audioSourcePlayer = GetComponent<AudioSource>();
        playerAnimator = GetComponent<Animator>();
        cameraScript = FindObjectOfType<CameraSoundController>();
        octopusScript = FindObjectOfType<OctopusController>();
        pointsController = FindObjectOfType<PointsController>();
        
    }

    // Método que se ejecuta una vez por frame.
    void Update()
    {
        // Si el jugador se puede mover (que es siempre excepto cuando gana el juego)
        if (canMove)
        {
            // Se mueve a izquierda o derecha en función de si se han pulsado las teclas correspondientes al eje horizontal (tal y como está configurado, las flechas direccionales).
            entradaX = Input.GetAxis("Horizontal");
            transform.position += new Vector3(entradaX, 0, 0) * speed * Time.deltaTime;

            // Si la entrada x es mayor que 0 o menor que 0, significa que se está moviendo, por lo que se activa la animación.
            if ((entradaX > 0 || entradaX < 0))
            {
                playerAnimator.SetBool("isRunning", true);
            }
            else
            {
                playerAnimator.SetBool("isRunning", false);
            }

            // Si se pulsa la tecla de mover a la derecha, se desactivará el flip, y por lo tanto se mostrará el sprite normal.
            if (entradaX > 0)
            {
                flipPlayer.flipX = false;
            }

            // Si se pulsa la tecla de mover a la derecha, se activará el flip, y por lo tanto se mostrará el sprite invertido.
            if (entradaX < 0)
            {
                flipPlayer.flipX = true;
            }

            // Teclas para saltar: flecha de arriba o barra espaciadora.
            // Si se presionan y el numero de saltos es inferior a 2, se producirá una fuerza hacia arriba.
            if ((Input.GetKeyDown(KeyCode.UpArrow) || Input.GetKeyDown(KeyCode.Space)) && jumpCont < 2)
            {
                JumpSound();
                playerRb.AddForce(Vector2.up * jumpForce, ForceMode2D.Impulse);
                playerAnimator.SetBool("isJumping", true);                      // Activa la animación de salto
                jumpCont++;                                                     // Aumenta el contador de salto en 1.
            }
        }
        
        // Si el numero de vidas totales es 0, el personaje se destruye.
        if(totalLifes == 0)
        {
            // Instanciamos el prefab de las particulas
            pointsController.UpdateMaxPoints();
            Instantiate(playerExplosionPrefab, transform.position, Quaternion.identity);
            isFinish = true;                        // Indicamos que el juego ha acabado.
            panelGameOver.SetActive(true);          // Activamos el panel de Game Over.
            cameraPlayer.transform.parent = null;   // Independizamos la cámara del Player.
            Destroy(gameObject);                    // Destruimos el objeto del player.
        }

    }

    // ======================================================== COLISIONES =======================================================
   private void OnCollisionEnter2D(Collision2D collision)
    {
        // Si el personaje entra en contacto con el suelo, igualará el contador de saltos a 0 y desactiva la animación de salto.
        if(collision.gameObject.tag == "Ground")
        {
            jumpCont = 0;
            playerAnimator.SetBool("isJumping", false);
        }

       

        // Si el personaje colisiona contra un pulpo y le ha herido, instancia la la explosión del enemigo y lo destruye.
        // Además ejerce una fuerza hacia arriba al player. 
        // Si el personaje colisiona contra un pulpo y no le ha herido, disminuye las vidas del player en 1.
        if(collision.gameObject.tag == "Enemy" && octopusScript.isHurt)
        {
            octopusScript.isHurt = false;
            GameObject octopusExplosion = Instantiate(enemyExplosionPrefab, collision.gameObject.transform.position, Quaternion.identity);
            ParticleSystem ps = octopusExplosion.GetComponent<ParticleSystem>();
            ps.Play();
            Destroy(octopusExplosion, ps.main.duration);    // Destruimos el prefab de la explosion una vez transcurrida la duración del sistema de particulas.

            Destroy(collision.gameObject);
            playerRb.AddForce(Vector2.up * pushForce, ForceMode2D.Impulse);
        }
        else if (collision.gameObject.tag == "Enemy")
        {

            totalLifes -= 1;
            // Si las vidas del player llegan a 0, se activa el sonido de muerte del personaje y el de Game Over.
            if (totalLifes == 0)
            {
                cameraScript.DeathSound();
                cameraScript.GameOverSound();
            }
            else
            {
                // Si el Player no ha muerto, se activa el sonido de daño.
                cameraScript.DamageSound();

                // Si el enemigo está a la derecha, nos empujará a la izquierda y hacia arriba para alejarnos del enemigo.
                if (collision.transform.position.x > transform.position.x)
                {
                    playerRb.AddForce(Vector2.left * pushForce, ForceMode2D.Impulse);
                    playerRb.AddForce(Vector2.up * pushForce, ForceMode2D.Impulse);
                }

                // Si el enemigo está al a izquierda, nos empujará a la derecha y hacia arriba para alejarnos del enemigo.
                if (collision.transform.position.x < transform.position.x)
                {
                    playerRb.AddForce(Vector2.right * pushForce, ForceMode2D.Impulse);
                    playerRb.AddForce(Vector2.up * pushForce, ForceMode2D.Impulse);
                }
            }

            
        }

        // Si el jugador colisiona con el "jefe" morirá al instante. El jefe no se puede matar.
        // 
        if (collision.gameObject.tag == "boss")
        {
            totalLifes = 0;
            cameraScript.DeathSound();
            cameraScript.GameOverSound();
        }
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        // Si el jugador colisiona con un objeto con tag "DeathGround" (en este caso el "agua") mmorirá al instante.
        if (collision.gameObject.tag == "DeathGround")
        {
            totalLifes = 0;
            cameraScript.GameOverSound();
            cameraScript.DeathSound();
        }

        // Si el jugador activa el trigger que tiene un pulpo en su cabeza, lo herirá (y lo matará).
        if(collision.gameObject.tag == "KillEnemyCollider")
        {
            octopusScript.isHurt = true;
        }

        // Si se pasa por el trigger del final del juego y se tiene el SoulFragment, se gana el juego.
        // Desactivamos también el movimiento del personaje y su animación de correr.
        // Activamos el panel de Victoria.
        if (collision.gameObject.tag == "Finish" && hasSoulFragment)
        {
            pointsController.UpdateMaxPoints();
            isFinish = true;
            canMove = false;
            playerAnimator.SetBool("isRunning", false);
            panelWin.SetActive(true);
        }

        // Si el player no tiene el Soul Fragment y traspasa el trigger de cuando está cerca de la meta, se activará el texto
        // que indica que lo necesita.
        if (collision.gameObject.tag == "NeedToWin" && !hasSoulFragment)
        {
            needToWinText.SetActive(true);
        }

        if(collision.gameObject.tag == "spawnCollider")
        {
            AudioSource audioSource = spawnBombs.GetComponent<AudioSource>();
            if (audioSource != null)
            {
                audioSource.enabled = true;
            }
        }

    }

    public void OnTriggerExit2D(Collider2D collision)
    {
        // Cuando el player sale del trigger del texto que indica que necesita el Soul Fragment, se desactivará el texto.
        if (collision.gameObject.tag == "NeedToWin" && !hasSoulFragment)
        {
            needToWinText.SetActive(false);
        }

        if (collision.gameObject.tag == "spawnCollider")
        {
            AudioSource audioSource = spawnBombs.GetComponent<AudioSource>();
            if (audioSource != null)
            {
                audioSource.enabled = false;
            }
        }
        
    }


    // =================================================== SONIDO ================================================

    public void OrbSound()
    {
        audioSourcePlayer.PlayOneShot(orbCollectSound);
    }

    public void JumpSound()
    {
        audioSourcePlayer.PlayOneShot(jump);
    }

    public void SoulSound()
    {
        audioSourcePlayer.PlayOneShot(soulSound);
    }

    public void HeartSound()
    {
        audioSourcePlayer.PlayOneShot(heartSound);
    }
    
}
