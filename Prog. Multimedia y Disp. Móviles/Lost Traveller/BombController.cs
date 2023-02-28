using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BombController : MonoBehaviour
{
    public GameObject explosionPrefab;
    private BombSpawn bombSpawnScript;
    private PlayerController playerController;
    // Start is called before the first frame update
    void Start()
    {
        bombSpawnScript = FindObjectOfType<BombSpawn>();
        playerController = FindObjectOfType<PlayerController>();
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        if(collision.gameObject.tag == "Player")
        {
            playerController.totalLifes = 0;
        }

        // Crea las partículas de la explosión y la elimina una vez terminada.
        GameObject explosion = Instantiate(explosionPrefab, transform.position, Quaternion.identity);
        ParticleSystem ps = explosion.GetComponent<ParticleSystem>();
        ps.Play();
        Destroy(explosion, ps.main.duration + 1.5f);

        bombSpawnScript.BombExplosion();
        Destroy(gameObject);
    }
}
